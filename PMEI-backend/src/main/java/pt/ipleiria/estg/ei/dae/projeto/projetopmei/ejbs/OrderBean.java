package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.OrderStatusBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Customer;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

import java.sql.Date;
import java.util.List;

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

    public void create(long statusCode, String customerCode, String packageTypeName){
        OrderStatusType status = orderStatusBean.find(statusCode);
        if(status == null){
            throw new RuntimeException("Invalid Order Status Code");
        }
        Customer customer = customerBean.find(customerCode);
        if(customer == null){
            throw new RuntimeException("Invalid Customer Code");
        }

        Order newOrder = new Order(status, customer);
        entityManager.persist(newOrder);

        // Procura se o tipo de package existe
        PackageType packageType = packageTypeBean.find(packageTypeName);
        if (packageType == null) {
            throw new RuntimeException("Invalid Package Type");
        }
        // Cria a package
        packageBean.create(packageType, newOrder);

    }

    public Order find(long id){
        var order = entityManager.find(Order.class, id);
        return order;
    }
    public List<Order> findAll(){
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }
    public Order findWithPackages(long id){
        var order = this.find(id);
        Hibernate.initialize(order.getPackageList());
        return order;
    }


}
