package adoption.client;

import adoption.dto.MonsterForm;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.core.MediaType;

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
    Uni<MonsterView> getAllMonsters();

}
