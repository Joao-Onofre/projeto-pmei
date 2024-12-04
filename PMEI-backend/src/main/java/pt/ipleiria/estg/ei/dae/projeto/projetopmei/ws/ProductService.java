package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.ProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.ProductBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;

import java.util.List;

@Path("product")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductService {
    @EJB
    private ProductBean productBean;

    // GET method to retrieve all products
    @GET
    @Path("/") // The relative URL path is “/api/product/”
    public List<ProductDTO> getAllProducts() {
        return ProductDTO.from(productBean.findAll());
    }

    @GET
    @Path("/search/{name}")
    public List<ProductDTO> getProduct(@PathParam("name") String name) {
        return ProductDTO.from(productBean.findByName(name));
    }

    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") long id) {
        Product product = productBean.find(id);
        return Response.ok(ProductDTO.from(product)).build();
    }

    @POST
    @Path("/")
    public Response createNewProduct(ProductDTO productDTO) {
        Product newProduct = productBean.create(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getProductTypeId()
        );

        return Response.status(Response.Status.CREATED).entity(ProductDTO.from(newProduct)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") long id, ProductDTO productDTO) {
        productBean.update(id, productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(), productDTO.getProductTypeId());

        Product updatedProduct = productBean.find(id);
        return Response.ok(ProductDTO.from(updatedProduct)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") long id) {
        Product product = productBean.find(id);
        productBean.delete(id);
        return Response.status(Response.Status.OK)
                .entity("Product with id " + id + " successfully deleted")
                .build();
    }
}
