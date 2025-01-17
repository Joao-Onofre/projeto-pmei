package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.ProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.ProductBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.helpers.FastExcelHelper;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.helpers.FileUploadForm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @POST
    @Path("/import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importProducts(@MultipartForm FileUploadForm form) {
        FastExcelHelper excelHelper = new FastExcelHelper();

        try {
            Map<Integer, List<String>> data = excelHelper.readExcel(form.getFile());

            if (data.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("The Excel file is empty.")
                        .build();
            }

            List<Product> products = processAndPersistProducts(data);
            return Response.status(Response.Status.CREATED)
                    .entity("Products imported successfully: " + products.size())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error processing file: " + e.getMessage())
                    .build();
        }
    }

    private List<Product> processAndPersistProducts(Map<Integer, List<String>> data) {
        List<Product> products = new ArrayList<>();
        boolean isFirstRow = true;
        int nameIndex = -1, descriptionIndex = -1, priceIndex = -1, typeIdIndex = -1;

        for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
            List<String> row = entry.getValue();

            if (isFirstRow) {
                for (int i = 0; i < row.size(); i++) {
                    String header = row.get(i).toLowerCase();
                    if (header.equals("name")) nameIndex = i;
                    if (header.equals("description")) descriptionIndex = i;
                    if (header.equals("price")) priceIndex = i;
                    if (header.equals("typeid")) typeIdIndex = i;
                }
                isFirstRow = false;
                if (nameIndex == -1 || descriptionIndex == -1 || priceIndex == -1 || typeIdIndex == -1) {
                    throw new IllegalArgumentException("Invalid headers. Expected: Name, Description, Price, TypeId.");
                }
                continue;
            }
            String name = row.get(nameIndex);
            String description = row.get(descriptionIndex);
            double price = Double.parseDouble(row.get(priceIndex));
            long typeId = Long.parseLong(row.get(typeIdIndex));

            Product product = productBean.create(name, description, (float) price, typeId);
            products.add(product);
        }

        return products;
    }
}
