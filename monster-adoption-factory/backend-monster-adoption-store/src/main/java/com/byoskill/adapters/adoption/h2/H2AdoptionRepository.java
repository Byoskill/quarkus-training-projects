package com.byoskill.adapters.adoption.h2;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.Duration;
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

    @Transactional
    @Override
    public Uni<Monster> addMonsterToAdopt(final Monster monster) {
        final MonsterEntity entity = MonsterEntity.fromModel(monster);
        entityManager.persist(entity);
        return Uni.createFrom().item(entity.toModel());
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

    @Transactional
    @Override
    public void deleteMonsterByUuid(final String uuid) {
        entityManager.createQuery("DELETE FROM MonsterEntity monster where monster.monsterUUID = :id");
        entityManager.flush();
    }

    @Transactional
    @Override
    public Uni<Monster> updateMonsterByUUID(final String uuid, final Monster monster) {
        final var query = """
                UPDATE MonsterEntity monster set monster.name = :name,
                monster.age = :age, 
                monster.description = :description, 
                monster.imageUrl = :imageUrl, 
                monster.location = :location, 
                monster.price = :price 
                where monster.monsterUUID = :uuid
                """;
        entityManager.createQuery(query)
                .setParameter("uuid", uuid)
                .setParameter("name", monster.getName())
                .setParameter("age", monster.getAge())
                .setParameter("description", monster.getDescription())
                .setParameter("imageUrl", monster.getImage_url())
                .setParameter("location", monster.getLocation())
                .setParameter("price", monster.getPrice())
                .executeUpdate();

        entityManager.flush();
        return Uni.createFrom().item(monster);
    }

    @Override
    public Multi<Monster> searchMonstersByAge(final Integer age) {
        final TypedQuery<MonsterEntity> query = entityManager.createQuery("SELECT monster from MonsterEntity monster where monster.age = :age", MonsterEntity.class);
        query.setParameter("age", age);
        final List<MonsterEntity> resultList = query.getResultList();
        return Multi.createFrom().items(resultList.stream()
                .map(MonsterEntity::toModel)
        );
    }

    @Transactional
    @Override
    public Uni<Monster> changeName(final Monster entityToBeUpdated, final String newName) {
        final Uni<Monster> monsterByUuid = getMonsterByUuid(entityToBeUpdated.getMonsterUUID());
        return monsterByUuid
                .map(monster -> {
                    monster.setName(newName);
                    return monster;
                })
                .log("monster::changeName")
                .ifNoItem().after(Duration.ofMillis(500)).fail()
                .onItem().invoke(updatedMonster -> {
                    entityManager.merge(updatedMonster);
                    entityManager.flush();
                });
    }
}
