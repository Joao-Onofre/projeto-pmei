package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
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
    @ManyToOne
    @JoinColumn(name = "customer_username", referencedColumnName = "username")
    private Customer customer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    private Date deliveryDate;

    private Date terminationDate;

    private boolean terminated;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Package> packageList = new ArrayList<Package>();

    @Version
    private int version;

    //-------------- Construtores ----------------
    public Order() {

    }
    public Order(OrderStatusType status, Customer customer, boolean terminated) {
        this.status = status;
        this.customer = customer;
        this.packageList = new ArrayList<Package>();
        this.terminated = false;
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

    public boolean isTerminated() {
        return terminated;
    }
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }
    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public List<Package> getPackageList() {
        return packageList;
    }
    public void setPackageList(List<Package> packageList) {
        this.packageList = packageList;
    }

}
