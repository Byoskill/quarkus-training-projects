package com.byoskill.auth.controllers;


import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Arrays;
import java.util.HashSet;

@Path("/login")
public class LoginResource {
    @Inject
    Template login;

    @Inject
    JsonWebToken jwt;

    public LoginResource() {
    }

    // This endpoint generates a JWT token if the username is test and the password is password.
    @POST
    @Path("/form")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response authenticate(
            @FormParam("username") final String username,
            @FormParam("password") final String password,
            @Context final HttpHeaders request) {

        if ("test".equals(username) && "password".equals(password)) {
            final String token =
                    Jwt.issuer("https://example.com/issuer")
                            .upn("jdoe@quarkus.io")
                            .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                            .claim(Claims.birthdate.name(), "2001-07-13")
                            .sign();

            return Response.ok(login.instance().data("access_token", token)
                    .data("refresh_token", token)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/refresh")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> refreshToken(
            @FormParam("username") final String username,
            @FormParam("password") final String password,
            @Context final HttpHeaders request) {


        return get(request);
    }

    @PermitAll
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> get(@Context final HttpHeaders request) {
        return Uni.createFrom()
                .item(login.instance().data("access_token", request.getHeaderString("access_token"))
                        .data("refresh_token", request.getHeaderString("refresh_token")));
    }
}
