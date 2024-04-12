package com.byoskill.controllers;

import com.byoskill.communication.client.CommunicationMessageService;
import com.byoskill.communication.model.WelcomeMessage;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("")
public class HomePage {
    
    
    @RestClient
    CommunicationMessageService communicationMessageService;
    
    @Inject
    Template index; 

    @Blocking
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        WelcomeMessage welcomeMessage = communicationMessageService.getWelcomeMessage();
        return index.data("motd", welcomeMessage.message());
    }
}
