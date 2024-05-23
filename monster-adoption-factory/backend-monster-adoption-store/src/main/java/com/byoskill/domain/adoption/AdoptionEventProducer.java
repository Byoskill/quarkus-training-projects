package com.byoskill.domain.adoption;

public interface AdoptionEventProducer {
    public void sendAdoptionAvailableEvent(String message);

    public void sendAdoptionFinalizedEvent(String message);
}
