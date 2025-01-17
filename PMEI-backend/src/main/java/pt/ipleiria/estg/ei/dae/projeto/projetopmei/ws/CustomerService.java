package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.CustomerDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.CustomerBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Customer;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.Authenticated;

import java.util.List;

@Path("customer")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
public class CustomerService {
	@EJB
	private CustomerBean customerBean;
	@Context
	private SecurityContext securityContext;

	@GET
	@Path("/")
	public List<CustomerDTO> getAllCustomers() {
		return CustomerDTO.from(customerBean.findAll());
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({"Administrator"})
	public Response createNewCustomer(CustomerDTO customerDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
		customerBean.create(
			customerDTO.getUsername(),
			customerDTO.getPassword(),
			customerDTO.getName(),
			customerDTO.getEmail()
		);
		Customer newCustomer = customerBean.find(customerDTO.getUsername());
		return Response.status(Response.Status.CREATED)
			.entity(CustomerDTO.from(newCustomer))
			.build();
	}

	@GET
	@Path("{username}")
	@RolesAllowed({"Customer", "Administrator"})
	public Response getCustomer(@PathParam("username") String username) throws MyEntityNotFoundException {
		var principal = securityContext.getUserPrincipal();

		if (!principal.getName().equals(username) && !securityContext.isUserInRole("Administrator")) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}

		var customer = customerBean.find(username);
		return Response.ok(CustomerDTO.from(customer)).build();
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({"Administrator"})
	public Response updateCustomer(CustomerDTO customerDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
		customerBean.update(
			customerDTO.getUsername(),
			customerDTO.getPassword(),
			customerDTO.getName(),
			customerDTO.getEmail()
		);
		return Response.status(Response.Status.OK).build();
	}

	@DELETE
	@Path("{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({"Administrator"})
	public Response deleteCustomer(@PathParam("username") String username) throws MyEntityNotFoundException {
		customerBean.delete(username);
		return Response.status(Response.Status.OK).build();
	}
}
