package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o" // JPQL
        )
})
public class Order {
    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private OrderStatusType status;
    private Customer customer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    private Date deliveryDate;
    @OneToMany(mappedBy = "order")
    private List<Package> packageList;

    //-------------- Construtores ----------------
    public Order() {

    }
    public Order(OrderStatusType status, Customer customer) {
        this.status = status;
        this.customer = customer;
        this.packageList = new ArrayList<Package>();
    }

    //-------------- Metodos ----------------

    //-------------- Getters / Setters ----------------
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public OrderStatusType getStatus() {
        return status;
    }
    public void setStatus(OrderStatusType status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<Package> getPackageList() {
        return packageList;
    }
    public void setPackageList(List<Package> packageList) {
        this.packageList = packageList;
    }

}
