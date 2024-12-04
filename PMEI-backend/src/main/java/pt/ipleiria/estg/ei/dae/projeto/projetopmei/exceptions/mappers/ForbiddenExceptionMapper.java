package pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.mappers;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class ForbiddenExceptionMapper
	implements ExceptionMapper<ForbiddenException> {
		private static final Logger logger =
			Logger.getLogger(ForbiddenException.class.getCanonicalName());

		@Override
		public Response toResponse(ForbiddenException e) {
		String errorMsg = e.getMessage();
		logger.warning("ERROR: " + errorMsg);
		return Response.status(Response.Status.BAD_REQUEST)
			.entity(errorMsg)
			.build();
	}
}
