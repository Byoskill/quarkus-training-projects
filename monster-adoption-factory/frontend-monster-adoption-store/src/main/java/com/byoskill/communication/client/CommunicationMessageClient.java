package com.byoskill.communication.client;

import com.byoskill.communication.model.WelcomeMessage;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/motd")
@RegisterRestClient(configKey = "communication-api")
public interface CommunicationMessageClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Uni<WelcomeMessage> getWelcomeMessage();
}