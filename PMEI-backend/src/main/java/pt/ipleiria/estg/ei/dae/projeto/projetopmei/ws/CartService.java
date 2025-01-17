package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.CartDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.CartBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.CustomerBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.ProductBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Cart;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Customer;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

@Path("cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartService {
    @EJB
    private CartBean cartBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private CustomerBean customerBean;

    @GET
    @Path("/customer/{username}")
    public Response getCartByCustomerUsername(@PathParam("username") String username) throws MyEntityNotFoundException {
        Customer customer = customerBean.find(username);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }

        Cart cart = cartBean.findByCustomer(customer);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cart not found for customer").build();
        }

        return Response.ok(CartDTO.from(cart)).build(); // Return CartDTO
    }

    @POST
    @Path("/customer/{username}/add")
    public Response addProductToCart(@PathParam("username") String username, @QueryParam("productId") long productId) throws MyEntityNotFoundException {
        Customer customer = customerBean.find(username);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }

        Cart cart = cartBean.findOrCreateCartForCustomer(customer);
        Product product = productBean.find(productId);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }

        cartBean.addProductToCart(cart, product);

        return Response.ok(CartDTO.from(cart)).build();
    }

    @DELETE
    @Path("/customer/{username}/remove/{productId}")
    public Response removeProductFromCart(@PathParam("username") String username, @PathParam("productId") long productId) throws MyEntityNotFoundException {
        Customer customer = customerBean.find(username);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }

        Cart cart = cartBean.findByCustomer(customer);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cart not found").build();
        }

        Product product = productBean.find(productId);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }

        cartBean.removeProductFromCart(cart, product);

        return Response.ok(CartDTO.from(cart)).build();
    }

}
