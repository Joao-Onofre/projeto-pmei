package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.OrderDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.typesDTOs.OrderStatusTypeDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.typesDTOs.PackageTypeDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.OrderStatusBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

import java.util.List;

@Path("packagetype") // relative url api/order
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PackageTypeService {

    @EJB
    private PackageTypeBean packageTypeBean;

    @GET
    @Path("/") //get all orders (Admins)
    public List<PackageTypeDTO> getAllPackageTypes() {
        return PackageTypeDTO.from(packageTypeBean.findAll());
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPackageType(PackageTypeDTO packageTypeDTO) {
        try {

            PackageType newPackageType = packageTypeBean.create(packageTypeDTO.getType());
            return Response.status(Response.Status.CREATED).entity(PackageTypeDTO.from(newPackageType)).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating the package type: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updatePackageType(@PathParam("id") long id, PackageTypeDTO packageTypeDTO) {
        try {
            packageTypeBean.update(
                    id,
                    packageTypeDTO.getType()
            );

            System.out.println("PACKAGE TYPE ID: "+id);
            System.out.println("PACKAGE TYPE TYPE: "+packageTypeDTO.getType());

            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error updating Package Type: " + e.getMessage()).build();
        }
    }
}
