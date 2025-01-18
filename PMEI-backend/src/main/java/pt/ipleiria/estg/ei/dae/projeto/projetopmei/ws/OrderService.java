package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.OrderDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.PackageDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.OrderBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;

import java.util.List;
import java.util.stream.Collectors;

@Path("order") // relative url api/order
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @GET
    @Path("/")
    public List<OrderDTO> getAllOrders() {
        return OrderDTO.from(orderBean.findAll());
    }

    @POST
    @Path("/")// create order (Customers)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewOrder (OrderDTO orderDTO){
        try {

            // Call the business logic to create the order
            Order newOrder = orderBean.create(
                    orderDTO.getCustomerUsername(),
                    orderDTO.getProductList()
            );

            return Response.status(Response.Status.CREATED).entity(OrderDTO.from(newOrder)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating the order: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}") //get all orders (Admins)
    public Response getOrderById(@PathParam("id") long id) {
        var order = orderBean.findWithPackages(id);
        var orderDTO = OrderDTO.from(order);

        orderDTO.setPackageList(PackageDTO.from(order.getPackageList()));
        return Response.ok(orderDTO).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateOrder(@PathParam("id") long id, OrderDTO orderDTO) {
        try {
            orderBean.update(
                    id,
                    orderDTO.getStatus()
            );
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error updating Order: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}") // endpoint for creating a new course
    public Response deleteCourse(@PathParam("id") long id) {
        try {
            orderBean.softDelete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error deleting Order: " + e.getMessage()).build();
        }
    }
}
