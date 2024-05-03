package com.byoskill;

import com.byoskill.adapters.adoption.h2.H2AdoptionRepository;
import com.byoskill.adapters.adoptions.memory.AdoptionMemoryRepository;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;

@Dependent
public class AdoptionConfiguration {

    @Produces
    @IfBuildProfile("h2")
    public AdoptionRepository H2AdoptionRepository(final EntityManager entityManager) {
        return new H2AdoptionRepository(entityManager);
    }

    @Produces
    @DefaultBean
    public AdoptionRepository memoryAdoptionRepository() {
        return new AdoptionMemoryRepository();
    }
}