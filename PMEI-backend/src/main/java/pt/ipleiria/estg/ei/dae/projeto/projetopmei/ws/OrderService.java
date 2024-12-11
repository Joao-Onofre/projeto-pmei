package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.OrderDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.OrderBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

@Path("order") // relative url api/order
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @GET
    @Path("/") //get all orders (Admins)
    public List<OrderDTO> getAllOrders() {
        return OrderDTO.from(orderBean.findAll());
    }

    @POST
    @Path("/")// create order (Customers)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewOrder (OrderDTO orderDTO){
        //vai receber o username
        // Extract the customer username
        String customerUsername = orderDTO.getCustomerUsername();

        // Extract the list of products
        List<Product> products = orderDTO.getProductList().stream()
                .map(productDTO -> new Product(
                        productDTO.getId(),
                        productDTO.getName(),
                        productDTO.getQuantity()
                ))
                .collect(Collectors.toList());

        // Create the order using the service layer
        Order order = orderBean.create(customerUsername, products);

    }

}
