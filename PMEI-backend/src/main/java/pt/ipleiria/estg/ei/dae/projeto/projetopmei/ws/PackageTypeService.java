package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.typesDTOs.OrderStatusTypeDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.typesDTOs.PackageTypeDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.OrderStatusBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;

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
}
