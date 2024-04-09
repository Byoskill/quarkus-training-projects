package com.byoskill;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/motd")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String motd() {
        return "Bienvenue sur notre site d'adoption";
    }
}
