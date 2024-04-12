package com.byoskill.communication.client;
import com.byoskill.communication.model.WelcomeMessage;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/motd")
@RegisterRestClient(configKey = "communication-api")
public interface CommunicationMessageService {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    WelcomeMessage getWelcomeMessage();
}