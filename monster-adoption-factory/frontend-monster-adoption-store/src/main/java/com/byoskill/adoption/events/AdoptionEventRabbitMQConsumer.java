package com.byoskill.adoption.events;

import io.quarkus.arc.profile.IfBuildProfile;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@IfBuildProfile(anyOf = "rabbitmq")
@ApplicationScoped
public class AdoptionEventRabbitMQConsumer {

    @Inject
    AdoptionService adoptionService;

    @Incoming("adoption-events")
    @Blocking
    public void process(JsonObject messagePayload) {
        AdoptionEvent event = messagePayload.mapTo(AdoptionEvent.class);
        if ("Nouvelle adoption disponible".equals(event.type)) {
            adoptionService.handleNewAdoption(event.message);
        } else if ("Adoption finalisée".equals(event.type)) {
            adoptionService.handleFinalizedAdoption(event.message);
        }
    }
}