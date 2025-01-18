package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.AuthDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.CustomerDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.auth.AuthSetPasswordDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.CustomerBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.UserBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyUserSetPasswordException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.Authenticated;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.TokenIssuer;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
	@Inject
	private TokenIssuer issuer;
	@EJB
	private UserBean userBean;
	@EJB
	private CustomerBean customerBean;
	@Context
	private SecurityContext securityContext;

	@POST
	@Path("/login")
	public Response authenticate(@Valid AuthDTO auth) {
		if (userBean.canLogin(auth.getUsername(), auth.getPassword())) {
			String token = issuer.issue(auth.getUsername());
			return Response.ok(token).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@PATCH
	@Path("/set-password")
	@Authenticated
	@PermitAll
	public Response setPassword(@Valid AuthSetPasswordDTO data) throws MyEntityNotFoundException, MyUserSetPasswordException, MyConstraintViolationException {
		String username = securityContext.getUserPrincipal().getName();
		userBean.setPassword(username, data.getOldPassword(), data.getNewPassword(), data.getConfirmPassword());
		return Response.ok().build();
	}

	@POST
	@Path("/register")
	public Response register(@Valid CustomerDTO data) {
		try {
			customerBean.create(data.getUsername(), data.getPassword(), data.getName(), data.getEmail());
			return Response.status(Response.Status.CREATED).build();
		} catch (MyEntityExistsException e) {
			return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
		} catch (MyConstraintViolationException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid data").build();
		}
	}
}
