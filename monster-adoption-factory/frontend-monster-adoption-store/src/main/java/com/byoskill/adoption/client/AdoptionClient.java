package com.byoskill.adoption.client;

import com.byoskill.adoption.model.MonsterForm;
import com.byoskill.adoption.model.MonsterView;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/adoptions")
@RegisterRestClient(configKey = "adoption-api")
public interface AdoptionClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<MonsterForm> addMonster(MonsterForm monsterForm);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<MonsterView> getMonsters();
}
