package communication.service;

import jakarta.inject.Singleton;

@Singleton
public class CommunicationService {
    private String[] messages = {"Bienvenue sur notre site d'adoptions", "plus de 10000 monstres disponibles à l'adoption", "les monstres les plus horribles à adopter."};

    public String[] getMessages() {
        return messages;
    }
}
