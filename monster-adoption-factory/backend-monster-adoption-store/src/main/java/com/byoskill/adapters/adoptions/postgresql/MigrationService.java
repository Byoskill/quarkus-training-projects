package com.byoskill.adapters.adoptions.postgresql;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.flywaydb.core.Flyway;

@IfBuildProfile(anyOf = "prod")
@ApplicationScoped
public class MigrationService {
    // Vous pouvez injecter l'objet si vous voulez l'utiliser manuellement
    @Inject
    Flyway flyway;

    @PostConstruct
    public void checkMigration() {
        // Cela affichera 1.0.0
        System.out.println(flyway.info().current().getVersion().toString());
    }
}