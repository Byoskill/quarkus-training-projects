package com.byoskill.adapters.adoptions.rabbitmq;


import com.byoskill.domain.adoption.AdoptionEventProducer;
import com.byoskill.domain.adoption.events.AdoptionEvent;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Vetoed;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@IfBuildProfile(anyOf = "rabbitmq")
@ApplicationScoped
@Vetoed
public class RabbitMqAdoptionEventProducer implements AdoptionEventProducer {

    @Channel("adoption-events")
    Emitter<AdoptionEvent> emitter;

    public void sendAdoptionAvailableEvent(String message) {
        emitter.send(new AdoptionEvent("Nouvelle adoption disponible", message));
    }

    public void sendAdoptionFinalizedEvent(String message) {
        emitter.send(new AdoptionEvent("Adoption finalisée", message));
    }
}