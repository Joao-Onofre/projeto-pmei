package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;


import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderStatusType {

    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status;
    @OneToMany(mappedBy = "status")
    private List<Order> orders;

    //-------------- Construtores --------------

    public OrderStatusType() {
    }
    public OrderStatusType(String status) {
        this.status = status;
        this.orders = new ArrayList<Order>();
    }

    //------------- Metodos ---------------------

    //--------------- Getters / Setters ---------------

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

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
