package com.byoskill.adapters.adoption.h2;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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
        entityManager.persist(MonsterEntity.fromModel(monster));
        return Uni.createFrom().item(monster);
    }

    @Override
    public Uni<Monster> getMonsterByUuid(final String uuid) {
        final TypedQuery<MonsterEntity> query = entityManager.createQuery("SELECT monster from MonsterEntity monster where monster.monsterUUID = :id", MonsterEntity.class);
        query.setParameter("id", uuid);
        final List<MonsterEntity> resultList = query.getResultList();
        return 1 == resultList.size() ? Uni.createFrom().item(resultList.get(0).toModel()) : Uni.createFrom().nullItem();
    }

    @Override
    public Multi<Monster> searchMonstersByName(final String name) {
        final TypedQuery<MonsterEntity> query = entityManager.createQuery("SELECT monster from MonsterEntity monster where monster.name = :name", MonsterEntity.class);
        query.setParameter("name", name);
        final List<MonsterEntity> resultList = query.getResultList();
        return Multi.createFrom().items(resultList.stream()
                .map(MonsterEntity::toModel)
        );
    }

    @Override
    public void deleteMonsterByUuid(final String uuid) {
        entityManager.createQuery("DELETE FROM MonsterEntity monster where monster.monsterUUID = :id");
        entityManager.flush();
    }

    @Override
    public Uni<Monster> updateMonsterByUUID(final String uuid, final Monster monster) {

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
