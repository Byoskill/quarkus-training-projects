package com.byoskill.communication.controllers;

import com.byoskill.adoption.client.AdoptionClient;
import com.byoskill.communication.client.CommunicationMessageClient;
import com.byoskill.communication.model.WelcomeMessage;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("")
public class HomePage {

    @Inject
    @RestClient
    CommunicationMessageClient communicationMessageClient;

    @Inject
    @RestClient
    AdoptionClient adoptionClient;

    @Inject
    Template index;

    @Blocking
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> get() {
        final Uni<String> welcomeMessage = communicationMessageClient.getWelcomeMessage().onItem().transform(WelcomeMessage::message);
        return Uni.createFrom().item(() -> index
                .data("monsters", adoptionClient.getMonsters())
                .data("motd", welcomeMessage)
        );
    }
}
