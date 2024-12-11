package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.ProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.ProductTypeDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.ProductTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.ProductType;

import java.util.List;

@Path("product-type")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductTypeService {

    @EJB
    private ProductTypeBean productTypeBean;

    @GET
    @Path("/")
    public List<ProductTypeDTO> getAllProductTypes() {
        List<ProductType> productTypes = productTypeBean.findAll();
        return ProductTypeDTO.from(productTypes);
    }

    @GET
    @Path("/{id}")
    public Response getProductType(@PathParam("id") long id) {
        ProductType productType = productTypeBean.find(id);
        return Response.ok(ProductTypeDTO.from(productType)).build();
    }

    @POST
    @Path("/")
    public Response createProductType(ProductTypeDTO productTypeDTO) {
        ProductType newProductType = productTypeBean.create(productTypeDTO.getType());
        return Response.status(Response.Status.CREATED).entity(ProductTypeDTO.from(newProductType)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProductType(@PathParam("id") long id, ProductTypeDTO productTypeDTO) {
        productTypeBean.update(id, productTypeDTO.getType());
        ProductType updatedProductType = productTypeBean.find(id);
        return Response.ok(ProductTypeDTO.from(updatedProductType)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProductType(@PathParam("id") long id) {
        productTypeBean.delete(id);
        return Response.ok("Product type with ID " + id + " successfully deleted").build();
    }
}
