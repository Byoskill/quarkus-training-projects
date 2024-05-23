package com.byoskill.adapters.adoptions.kafka;


import com.byoskill.domain.adoption.AdoptionEventProducer;
import com.byoskill.domain.adoption.events.AdoptionEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class KafkaMqAdoptionEventProducer implements AdoptionEventProducer {

    @Channel("adoption-events")
    Emitter<AdoptionEvent> emitter;
    
    public void sendAdoptionAvailableEvent(String message) {
        emitter.send(new AdoptionEvent("Nouvelle adoption disponible", message));
    }

    public void sendAdoptionFinalizedEvent(String message) {
        emitter.send(new AdoptionEvent("Adoption finalisée", message));
    }
}