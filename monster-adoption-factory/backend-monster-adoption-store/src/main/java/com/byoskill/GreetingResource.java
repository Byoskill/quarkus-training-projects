package com.byoskill;

import java.util.Map;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/motd")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Map<String, Object>> motd() {

        return Uni.createFrom().item(Map.of("message","Bienvenue sur notre site d'adoption"));
    }
}
