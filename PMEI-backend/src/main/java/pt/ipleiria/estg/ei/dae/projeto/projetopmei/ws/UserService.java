package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.UserDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.UserBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.Authenticated;

import java.util.List;

@Path("user")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
public class UserService {
	@EJB
	private UserBean userBean;

	@GET
	@Path("/")
	public List<UserDTO> getAllUsers() {
		return UserDTO.from(userBean.findAll());
	}

	@GET
	@Path("{username}")
	public Response getUser(@PathParam("username") String username) throws MyEntityNotFoundException {
		var user = userBean.find(username);
		return Response.ok(UserDTO.from(user)).build();
	}
}
