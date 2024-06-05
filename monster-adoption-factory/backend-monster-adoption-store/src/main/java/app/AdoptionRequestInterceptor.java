package app;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@AutorizationRequired
@Provider
public class AdoptionRequestInterceptor implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String uriInfo = requestContext.getUriInfo().getPath();

        if (null == requestContext.getHeaderString("Authorization")) {
            throw new RuntimeException();
        }
    }
}
