package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;

import java.util.List;

@Stateless
public class OrderStatusBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String status){
        var orderStatus = new OrderStatusType(status);
        entityManager.persist(orderStatus);
    }

    public OrderStatusType find(long code) {
        OrderStatusType orderStatus = entityManager.find(OrderStatusType.class, code);
        return orderStatus;
    }
}
