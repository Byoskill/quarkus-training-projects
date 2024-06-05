package communication.client;

import communication.dto.WelcomeMessage;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/motd")
@RegisterRestClient(configKey = "communication-api")
public interface CommunicationMessageClient {

    @GET
    Uni<WelcomeMessage> getWelcomeMessage();
}