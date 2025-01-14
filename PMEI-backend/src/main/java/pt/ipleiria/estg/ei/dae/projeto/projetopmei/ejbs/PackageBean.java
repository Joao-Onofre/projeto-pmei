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
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;
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

    public Package create(Order order, PackageType packageType){
        Package newPackage = new Package(packageType, order);
        entityManager.persist(newPackage); // Save the new package
        order.getPackageList().add(newPackage); // Link the package to the order
        return newPackage;

    }

    /*
    public Package update(long packageId, List<PackageProductDTO> packageProduct, long newPckgTypeId){
        Package pckg = find(packageId);
        if(pckg == null) {
            throw new RuntimeException("Package doesnt exist");
        }

        PackageType newPckgType = packageTypeBean.findById(newPckgTypeId);
        if(newPckgType == null){
            throw new RuntimeException("Package Type doesnt exist");
        }

        // Check if the product list is empty
        if (products == null || products.isEmpty()) {
            pckg.setPackageType(newPckgType);
            if(newPckgType.getId() == 2){
                //TODO meter aqui o codigo para adicionar sensor
            }
        }else{
            for (ProductDTO productDTO : products) {
                Product product = productBean.find(productDTO.getId());
                if (product == null) {
                    throw new RuntimeException("Invalid Product ID: " + productDTO.getId());
                }

                if (product.getProductType().getId() == 1 || product.getProductType().getId() == 2){
                    if(pckg.getPackageType().getId() != 2){
                        throw new RuntimeException("To add Fresh/Frozen Food products, the Package must be type 'Isometric'");
                    }
                    // Create a PackageProduct object with the quantity from ProductDTO
                    PackageProduct packageProduct = new PackageProduct();
                    packageProduct.setProduct(product);
                    packageProduct.setPack(pckg);
                    packageProduct.setQuantity((int) productDTO.getQuantity()); // Get the quantity from ProductDTO

                    // Add the PackageProduct to the Package
                    pckg.getPackageProducts().add(packageProduct);

                    // Persist the PackageProduct
                    entityManager.persist(packageProduct);

                }
            }
            // Persist the updated Package
            entityManager.merge(pckg);
            entityManager.persist(pckg);
            entityManager.flush();
        }
        return pckg;
    }
    */

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
}
