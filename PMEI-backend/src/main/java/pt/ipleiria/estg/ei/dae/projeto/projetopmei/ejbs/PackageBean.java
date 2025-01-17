package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.PackageProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.ProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.SensorTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.StatusTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class PackageBean {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private PackageTypeBean packageTypeBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private SensorTypeBean sensorTypeBean;
    @EJB
    private StatusTypeBean statusTypeBean;
    @EJB
    private CustomerBean customerBean;
    @EJB
    private PackageBean packageBean;

    public Package create(Order order, PackageType packageType){
        Package newPackage = new Package(packageType, order);
        entityManager.persist(newPackage); // Save the new package
        order.getPackageList().add(newPackage); // Link the package to the order

        if(packageType.getType().equals("Isometric")){
            List<Sensor> sensors = sensorBean.findByTypeAndStatus(1, 2);
            if(sensors == null || sensors.isEmpty()){
                throw new RuntimeException("Não existem sensores dispiniveis");
            }

            //vai buscar o primeiro sensor da lista
            Sensor sensor = sensors.get(0);

            //relaciona o sensor com a package
            newPackage.getSensorList().add(sensor);
            sensor.setPack(newPackage);
            sensor.setStatusType(statusTypeBean.findById((long)1));//sensor fica ativo

        }
        return newPackage;

    }


    public Package update(long packageId, List<PackageProductDTO> products, long newPckgTypeId) {
        Package pckg = find(packageId);
        if (pckg == null) {
            throw new RuntimeException("Package doesn't exist");
        }

        PackageType newPckgType = packageTypeBean.findById(newPckgTypeId);
        if (newPckgType == null) {
            throw new RuntimeException("Package Type doesn't exist");
        }

        Order order = pckg.getOrder();

        // Check if the product list is empty
        if (products == null || products.isEmpty()) {
            pckg.setPackageType(newPckgType);
            if (newPckgType.getId() == 2) {
                // TODO: Add sensor logic for this package type
            }
        } else {
            for (PackageProductDTO productDTO : products) {
                Product product = productBean.find(productDTO.getProduct());
                if (product == null) {
                    throw new RuntimeException("Invalid Product ID: " + productDTO.getId());
                }

                String productType = product.getProductType().getType();
                Package newPckg = null;
                PackageType pckgType = null;

                switch (productType) {
                    case "Fresh Food":
                    case "Frozen Food":
                        pckgType = packageTypeBean.findById(2); // Refrigerated Package Type
                        newPckg = packageBean.findWithOrder(order, pckgType);
                        if (newPckg == null && pckgType != null) {
                            newPckg = packageBean.create(order, pckgType);
                        }
                        break;

                    case "Appliances":
                        pckgType = packageTypeBean.findById(4); // Appliances Package Type
                        newPckg = packageBean.findWithOrder(order, pckgType);
                        if (newPckg == null && pckgType != null) {
                            newPckg = packageBean.create(order, pckgType);
                        }
                        break;

                    case "Canned Food":
                    case "Clothes":
                        pckgType = packageTypeBean.findById(5); // Standard Package Type
                        newPckg = packageBean.findWithOrder(order, pckgType);
                        if (newPckg == null && pckgType != null) {
                            newPckg = packageBean.create(order, pckgType);
                        }
                        break;

                    default:
                        throw new RuntimeException("Unknown product type: " + productType);
                }

                if (pckgType != null) {

                    PackageProduct packageProduct = new PackageProduct();
                    packageProduct.setProduct(product);
                    packageProduct.setPack(newPckg);
                    packageProduct.setQuantity((int) productDTO.getQuantity());

                    newPckg.getPackageProducts().add(packageProduct);


                    entityManager.persist(packageProduct);
                }
            }
        }

        // Persist the updated Package (for the original package's type update)
        pckg.setPackageType(newPckgType);
        entityManager.merge(pckg);
        entityManager.flush();

        return pckg;
    }



    public void delete(long id) throws MyEntityNotFoundException{
        Package pckg = find(id);
        if(pckg == null){
            throw new MyEntityNotFoundException("Order" + id + "not found");
        }

        // Clear the relationships with PackageProducts
        if (pckg.getPackageProducts() != null && !pckg.getPackageProducts().isEmpty()) {
            for (PackageProduct packageProduct : pckg.getPackageProducts()) {
                entityManager.remove(packageProduct); // Explicitly remove each PackageProduct
            }
            pckg.getPackageProducts().clear(); // Clear the list to detach the products
        }

        // Remove the package from the associated order's package list
        if (pckg.getOrder() != null) {
            pckg.getOrder().getPackageList().remove(pckg);
        }

        // Remove the package from the database
        entityManager.remove(pckg);
    }

    public Package find(long id){
        var packagee = entityManager.find(Package.class, id);
        return packagee;
    }
    public Package findByName(String typeName){
        var packagee = entityManager.find(Package.class, typeName);
        return packagee;
    }
    public List<Package> findAll(){
        return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
    }
    public Package findWithProducts(long id){
        var packagee = this.find(id);
        Hibernate.initialize(packagee.getPackageProducts());
        return packagee;
    }
    public Package findWithSensors(long id){
        var packagee = this.find(id);
        Hibernate.initialize(packagee.getSensorList());
        return packagee;
    }

    public Package findWithOrder(Order order, PackageType packageTypeId){
        for (Package pkg : order.getPackageList()) {
            if (pkg.getId() == packageTypeId.getId()) {
                return pkg;
            }
        }
        return null;
    }

    public List<Package> findCustomerPackages(String username) throws MyEntityNotFoundException {
        Customer customer = customerBean.find(username);
        if(customer == null) {
            throw new RuntimeException("O Customer não existe");
        }

        return entityManager.createNamedQuery("getAllCustomerPackages", Package.class)
                .setParameter("username", username)
                .getResultList();
    }
}
