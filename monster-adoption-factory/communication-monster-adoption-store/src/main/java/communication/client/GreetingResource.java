package communication.client;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import communication.service.CommunicationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "communication")
@Path("/motd")
public class GreetingResource {

    private CommunicationService communicationService;

    @Inject
    public GreetingResource(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> motd() {

        String[] messages = communicationService.getMessages();
        return Map.of("motd", messages[ThreadLocalRandom.current().nextInt(messages.length)]);
    }
}
