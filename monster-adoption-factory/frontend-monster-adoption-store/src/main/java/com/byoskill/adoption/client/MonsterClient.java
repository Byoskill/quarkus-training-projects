package com.byoskill.adoption.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.byoskill.adoption.model.MonsterForm;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/adoptions")
@RegisterRestClient(configKey = "adoption-api")
public interface MonsterClient {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<MonsterForm> addMonster(MonsterForm monsterForm);
    
}
