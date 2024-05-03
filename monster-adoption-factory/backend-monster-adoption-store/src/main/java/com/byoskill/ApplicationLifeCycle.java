package com.byoskill;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ApplicationLifeCycle {

    @Inject
    Logger logger;

    void onStart(@Observes final StartupEvent ev) {
        logger.info("The application is starting with profile " + ProfileManager.getActiveProfile());
    }
}