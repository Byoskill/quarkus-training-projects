package com.byoskill.auth.controllers;


import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Arrays;
import java.util.HashSet;

@Path("/login")
public class LoginResource {
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
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
                            .claim("microservice", "auth-service")
                            .sign();

            return Response.ok(login.instance().data("access_token", token)
                    .data("refresh_token", token)).build();
        }
        return Response.ok(login.instance()
                        .data(ACCESS_TOKEN, "invalid")
                        .data(REFRESH_TOKEN, "invalid"))
                .build();
    }

    @POST
    @Path("/refresh")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("User")
    public Uni<TemplateInstance> refreshToken(@Context final HttpHeaders request) {


        return get(request);
    }

    @PermitAll
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> get(@Context final HttpHeaders request) {
        return Uni.createFrom()
                .item(login.instance()
                        .data(ACCESS_TOKEN, request.getHeaderString("access_token"))
                        .data(REFRESH_TOKEN, request.getHeaderString("refresh_token")));
    }


    @GET
    @Path("/test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    //@RolesAllowed("User")
    public Response testAuth(@Context final SecurityContext ctx) {

        if (jwt.containsClaim("microservice") && jwt.getClaim("microservice").equals("auth-service")) {
            return Response.ok(getResponseString(ctx)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Cannot check the access token").build();
        }
    }

    private String getResponseString(final SecurityContext ctx) {
        final String name;
        if (null == ctx.getUserPrincipal()) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello + %s,"
                        + " isHttps: %s,"
                        + " authScheme: %s,"
                        + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt());
    }

    private boolean hasJwt() {
        return null != jwt.getClaimNames();
    }

}
