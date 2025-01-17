package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.typesDTOs;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderStatusTypeDTO implements Serializable {
    private long id;
    private String status;

    public OrderStatusTypeDTO(long id, String status) {
        this.id = id;
        this.status = status;
    }

    public static List<OrderStatusTypeDTO> from(List<OrderStatusType> statuses) {
        return statuses.stream()
                .map(status -> new OrderStatusTypeDTO(status.getId(), status.getStatus()))
                .collect(Collectors.toList());
    }

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
}
