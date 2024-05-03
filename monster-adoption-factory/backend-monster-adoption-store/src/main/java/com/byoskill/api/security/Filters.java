package com.byoskill.api.security;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import java.util.Optional;

class Filters {

    @ServerRequestFilter
    public Optional<Response> getFilter(final ContainerRequestContext ctx) {
        // only allow GET methods for now
        if (ctx.getMethod().equals(HttpMethod.PATCH)) {
            return Optional.of(Response.status(Response.Status.METHOD_NOT_ALLOWED).build());
        }
        return Optional.empty();
    }
}