package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.ProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.ProductTypeDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.ProductTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.ProductType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.helpers.FastExcelHelper;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.helpers.FileUploadForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @POST
    @Path("/import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importProductTypes(@MultipartForm FileUploadForm form) {
        FastExcelHelper excelHelper = new FastExcelHelper();

        try {
            Map<Integer, List<String>> data = excelHelper.readExcel(form.getFile());

            if (data.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("The Excel file is empty.")
                        .build();
            }

            List<ProductType> productTypes = processAndPersistProductTypes(data);
            return Response.status(Response.Status.CREATED)
                    .entity("Product types imported successfully: " + productTypes.size())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error processing file: " + e.getMessage())
                    .build();
        }
    }

    private List<ProductType> processAndPersistProductTypes(Map<Integer, List<String>> data) {
        List<ProductType> productTypes = new ArrayList<>();
        boolean isFirstRow = true;

        int typeIndex = -1;

        for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
            List<String> row = entry.getValue();

            if (isFirstRow) {
                for (int i = 0; i < row.size(); i++) {
                    String header = row.get(i).toLowerCase();
                    if (header.equals("type")) typeIndex = i;
                }
                isFirstRow = false;

                if (typeIndex == -1) {
                    throw new IllegalArgumentException("Invalid headers. Expected: Type.");
                }
                continue;
            }

            String type = row.get(typeIndex);

            ProductType productType = productTypeBean.create(type);
            productTypes.add(productType);
        }
        return productTypes;
    }
}
