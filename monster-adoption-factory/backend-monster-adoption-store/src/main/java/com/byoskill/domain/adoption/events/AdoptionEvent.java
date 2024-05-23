package com.byoskill.domain.adoption.events;

/**
 * This class describes the events we send through the event bus.
 */
public class AdoptionEvent {
    public String type;
    public String message;

    public AdoptionEvent() {
    }

    public AdoptionEvent(String type, String message) {
        this.type = type;
        this.message = message;
    }
}