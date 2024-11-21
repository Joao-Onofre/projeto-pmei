package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    //-------------- Atributos ----------------
    @Id
    private long id;
    private String status;
    private String customer;//está private string mas isto é para mudar para "private Customer" para cada order estar interligada com quem a encomendou
    private Date creationDate;
    private Date deliveryDate;
    @OneToMany(mappedBy = "order")
    private List<Package> packageList;

    //-------------- Construtores ----------------
    public Order() {

    }
    public Order(long id, String status, String customer, Date creationDate, Date deliveryDate) {
        this.id = id;
        this.status = status;
        this.customer = customer;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
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
