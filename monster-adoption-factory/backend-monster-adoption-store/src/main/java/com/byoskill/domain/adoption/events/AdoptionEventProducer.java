package com.byoskill.domain.adoption.events;

public interface AdoptionEventProducer {
    public void sendAdoptionAvailableEvent(String message);

    public void sendAdoptionFinalizedEvent(String message);
}
