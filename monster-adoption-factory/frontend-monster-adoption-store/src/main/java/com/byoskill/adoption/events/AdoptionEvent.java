package com.byoskill.adoption.events;

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