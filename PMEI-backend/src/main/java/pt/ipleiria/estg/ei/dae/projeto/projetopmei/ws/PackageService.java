package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.OrderDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.PackageDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.PackageProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.OrderBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.PackageBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Customer;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

import java.util.List;

@Path("package") // relative url api/order
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PackageService {

    @EJB
    private PackageBean packageBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private PackageTypeBean packageTypeBean;

    @GET
    @Path("/") //get all packages
    public List<PackageDTO> getAllPackages() {
        return PackageDTO.from(packageBean.findAll());
    }


    @POST
    @Path("/")// create package
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewPackage (PackageDTO packageDTO){
        PackageType pckgType = packageTypeBean.findById(packageDTO.getPackageType());
        Order order = orderBean.find(packageDTO.getOrder());
        try {
            // Call the business logic to create the order
            Package newPackage = packageBean.create(
                    order,
                    pckgType
            );

            return Response.status(Response.Status.CREATED).entity(PackageDTO.from(newPackage)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating the package: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}") //get all orders (Admins)
    public Response getPackageById(@PathParam("id") long id) {
        var pckg = packageBean.findWithProducts(id);
        var packageDTO = PackageDTO.from(pckg);

        packageDTO.setPackageProducts(PackageProductDTO.from(pckg.getPackageProducts()));
        return Response.ok(packageDTO).build();
    }


    @PUT
    @Path("/{id}")
    public Response updatePackage(@PathParam("id") long id, PackageDTO packageDTO) {
        try {
            packageBean.update(
                    id,
                    packageDTO.getPackageProducts(),
                    packageDTO.getPackageType()

            );
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error updating Order: " + e.getMessage()).build();
        }
    }


    @DELETE
    @Path("/{id}") // endpoint for creating a new course
    public Response deletePackage(@PathParam("id") long id) {
        try {
            packageBean.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error deleting Order: " + e.getMessage()).build();
        }
    }

}
