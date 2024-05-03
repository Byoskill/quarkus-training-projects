package com.byoskill.adapters.adoption.h2;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class H2AdoptionRepository implements AdoptionRepository {

    private final EntityManager entityManager;

    @Inject
    public H2AdoptionRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Multi<Monster> getAllMonsters() {
        final List<MonsterEntity> monsters = entityManager
                .createQuery("SELECT monster from MonsterEntity monster")
                .getResultList();
        return Multi.createFrom().items(monsters.stream()
                .map(MonsterEntity::toModel)
        );
    }

    @Override
    public Uni<Monster> addMonsterToAdopt(final Monster monster) {
        return null;
    }

    @Override
    public Uni<Monster> getMonsterByUuid(final String id) {
        return null;
    }

    @Override
    public Multi<Monster> searchMonstersByName(final String name) {
        return null;
    }

    @Override
    public void deleteMonsterById(final String id) {

    }

    @Override
    public Uni<Monster> updateMonsterByUUID(final String id, final Monster monster) {
        return null;
    }

    @Override
    public Multi<Monster> searchMonstersByAge(final Integer age) {
        return null;
    }

    @Override
    public Uni<Monster> changeName(final Monster entityToBeUpdated, final String newName) {
        return null;
    }
}
