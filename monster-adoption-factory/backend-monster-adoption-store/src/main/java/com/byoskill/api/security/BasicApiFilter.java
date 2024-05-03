package com.byoskill.api.security;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@ApiFilter
@Provider
public class BasicApiFilter implements ContainerResponseFilter {
    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
            throws IOException {
        // Detect if the HTTP Header containing the API Key is missing
        if (null == requestContext.getHeaderString("x-api-key")) {
            throw new WebApplicationException("Missing API Key", 403);
        }
    }
}