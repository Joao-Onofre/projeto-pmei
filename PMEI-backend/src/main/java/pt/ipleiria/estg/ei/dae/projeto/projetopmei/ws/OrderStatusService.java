package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.typesDTOs.OrderStatusTypeDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.OrderStatusBean;

import java.util.List;

@Path("orderstatus") // relative url api/order
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderStatusService {

    @EJB
    private OrderStatusBean orderStatusBean;

    @GET
    @Path("/") //get all orders (Admins)
    public List<OrderStatusTypeDTO> getAllOrderStatus() {
        return OrderStatusTypeDTO.from(orderStatusBean.findAll());
    }
}
