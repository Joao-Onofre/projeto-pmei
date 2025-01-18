package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.PackageProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.ProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.OrderStatusBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.StatusTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private OrderStatusBean orderStatusBean;
    @EJB
    private CustomerBean customerBean;
    @EJB
    private PackageBean packageBean;
    @EJB
    private PackageTypeBean packageTypeBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private StatusTypeBean statusTypeBean;

    public Order create(String customerUsername, List<ProductDTO> products) throws MyEntityNotFoundException {
        Customer customer = customerBean.find(customerUsername);
        if(customer == null) {
            throw new RuntimeException("O Customer não existe");
        }

        OrderStatusType status = orderStatusBean.find(1);
        if(status == null){
            throw new RuntimeException("O Customer não existe");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(status);
        entityManager.persist(order);

        for (ProductDTO productDTO : products) {
            Product product = productBean.find(productDTO.getId());
            if (product == null) {
                throw new RuntimeException("Invalid Product ID: " + productDTO.getId());
            }

            String productType = product.getProductType().getType();
            Package newPckg = null;
            PackageType pckgType = null;

            switch(productType){
                case "Fresh Food":
                case "Frozen Food":
                    pckgType = packageTypeBean.findById(2);
                    newPckg = packageBean.findWithOrder(order, pckgType);
                    if(newPckg == null && pckgType != null){
                        newPckg = packageBean.create( order, pckgType);
                    }
                    break;

                case "Appliances":
                    pckgType = packageTypeBean.findById(4);
                    newPckg = packageBean.create(order, pckgType);
                    break;

                case "Canned Food":
                case "Clothes":
                    pckgType = packageTypeBean.findById(5);
                    newPckg = packageBean.findWithOrder(order, pckgType);
                    if(newPckg == null && pckgType != null){
                        newPckg = packageBean.create(order, pckgType);
                    }
                    break;

                default:
                    throw new RuntimeException("Unknown product type: " + productType);
            }
            // Create a PackageProduct object with the quantity from ProductDTO
            PackageProduct packageProduct = new PackageProduct();
            packageProduct.setProduct(product);
            packageProduct.setPack(newPckg);
            packageProduct.setQuantity((int) productDTO.getQuantity()); // Get the quantity from ProductDTO

            // Add the PackageProduct to the Package
            newPckg.getPackageProducts().add(packageProduct);


            // Persist the PackageProduct
            entityManager.persist(packageProduct);

            // Add the Package to the Order (if not already added)
            if (!order.getPackageList().contains(newPckg)) {
                order.getPackageList().add(newPckg);
            }

            // Persist the updated Package
            entityManager.merge(newPckg);
        }
        order.setCreationDate(new Date());
        entityManager.persist(order);
        entityManager.flush();
        return order;
    }

    public Order update(long id, long statusId) throws MyEntityNotFoundException{
        Order order = find(id);
        if(order == null){
            throw new MyEntityNotFoundException("Order" + id + "not found");
        }

        OrderStatusType status = orderStatusBean.find(statusId);
        if(status == null){
            throw new MyEntityNotFoundException("Status" + statusId + "not found");
        }

        //Previnir concorrencia
        entityManager.lock(order, LockModeType.OPTIMISTIC);
        order.setStatus(status);

        String statusName = status.getStatus();
        if(statusName.equals("Delivered")){
            order.setDeliveryDate(new Date());

            // Criar um alerta
            Alert alert = new Alert();
            alert.setMessage("Order " + id + " delivered.");

            // Persistir o alerta
            entityManager.persist(alert);

            LOGGER.info("Alert created: " + alert.getMessage());
            order.setTerminated(true);
        } else if (statusName.equals("Canceled")) {
            order.setTerminationDate(new Date());
            order.setTerminated(true);
        }

        if (statusName.equals("Delivered") || statusName.equals("Canceled")){

            StatusType sensorStatus = statusTypeBean.findById((long)2);

            // Unlink sensors and set them to inactive
            for (Package pkg : order.getPackageList()) {
                for (Sensor sensor : pkg.getSensorList()) {
                    sensor.setStatusType(sensorStatus); // Set sensor status to inactive
                    sensor.setPack(null); // Unlink sensor from package
                    entityManager.merge(sensor); // Persist the changes
                }
                pkg.getSensorList().clear(); // Clear the sensors list for the package
                entityManager.merge(pkg); // Persist the package changes
            }
        }

        entityManager.merge(order);
        return order;
    }

    public Order softDelete(long id) throws MyEntityNotFoundException{
        Order order = find(id);
        if(order == null){
            throw new MyEntityNotFoundException("Order" + id + "not found");
        }

        entityManager.lock(order, LockModeType.OPTIMISTIC);
        order.setTerminated(true);
        order.setTerminationDate(new Date());

        entityManager.merge(order);
        return order;
    }

    public Order find(long id){
        var order = entityManager.find(Order.class, id);
        return order;
    }
    public List<Order> findAll(){
        List<Order> orders = entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
        for (Order order : orders) {
            order.getPackageList().size(); // Force initialization of the lazy collection
        }
        return orders;
    }
    public Order findWithPackages(long id){
        var order = this.find(id);
        Hibernate.initialize(order.getPackageList());
        return order;
    }
    public List<Order> findCustomerOrders(String username) throws MyEntityNotFoundException {
        Customer customer = customerBean.find(username);
        if(customer == null) {
            throw new RuntimeException("O Customer não existe");
        }

        return entityManager.createNamedQuery("getAllCustomerOrders", Order.class)
                .setParameter("username", username)
                .getResultList();
    }
}
