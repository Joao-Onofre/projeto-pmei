package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.AdministratorDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.AdministratorBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Administrator;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.Authenticated;

import java.util.List;

@Path("administrator")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed({"Administrator"})
public class AdministratorService {
	@EJB
	private AdministratorBean administatorBean;

	@GET
	@Path("/")
	public List<AdministratorDTO> getAllAdministrators() {
		return AdministratorDTO.from(administatorBean.findAll());
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewAdministrator(AdministratorDTO administratorDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
		administatorBean.create(
			administratorDTO.getUsername(),
			administratorDTO.getPassword(),
			administratorDTO.getName(),
			administratorDTO.getEmail()
		);
		Administrator newAdministrator = administatorBean.find(administratorDTO.getUsername());
		return Response.status(Response.Status.CREATED)
			.entity(AdministratorDTO.from(newAdministrator))
			.build();
	}

	@GET
	@Path("{username}")
	public Response getAdministrator(@PathParam("username") String username) throws MyEntityNotFoundException {
		var administrator = administatorBean.find(username);
		return Response.ok(AdministratorDTO.from(administrator)).build();
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTeacher(AdministratorDTO administratorDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
		administatorBean.update(
			administratorDTO.getUsername(),
			administratorDTO.getPassword(),
			administratorDTO.getName(),
			administratorDTO.getEmail()
		);
		return Response.status(Response.Status.OK).build();
	}

	@DELETE
	@Path("{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAdministrator(@PathParam("username") String username) throws MyEntityNotFoundException {
		administatorBean.delete(username);
		return Response.status(Response.Status.OK).build();
	}
}
