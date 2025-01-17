package pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyUserSetPasswordException;

import java.util.logging.Logger;

@Provider
public class MyUserSetPasswordExceptionMapper
	implements ExceptionMapper<MyUserSetPasswordException> {
	private static final Logger logger =
		Logger.getLogger(MyUserSetPasswordException.class.getCanonicalName());

	@Override
	public Response toResponse(MyUserSetPasswordException e) {
		String errorMsg = e.getMessage();
		logger.warning("ERROR: " + errorMsg);
		return Response.status(Response.Status.BAD_REQUEST)
			.entity(errorMsg)
			.build();
	}
}
