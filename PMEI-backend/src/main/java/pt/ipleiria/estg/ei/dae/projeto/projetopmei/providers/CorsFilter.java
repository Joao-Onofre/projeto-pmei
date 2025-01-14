package pt.ipleiria.estg.ei.dae.projeto.projetopmei.providers;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

public class CorsFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Headers","*");
        if (requestContext.getMethod().equals("OPTIONS")) {
            responseContext.setStatus(Response.Status.ACCEPTED.getStatusCode());
        }
    }
}
