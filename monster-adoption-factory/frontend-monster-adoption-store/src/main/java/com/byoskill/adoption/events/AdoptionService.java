package com.byoskill.adoption.events;


import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdoptionService {

    public void handleNewAdoption(String message) {
        // Logique pour gérer une nouvelle adoption
        System.out.println("Nouvelle adoption disponible : " + message);
    }

    public void handleFinalizedAdoption(String message) {
        // Logique pour gérer une adoption finalisée
        System.out.println("Adoption finalisée : " + message);
    }
}