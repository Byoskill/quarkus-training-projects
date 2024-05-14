package com.byoskill.adapters.adoptions.h2;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.mapping.SearchMapping;
import org.hibernate.search.mapper.orm.session.SearchSession;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@IfBuildProfile(anyOf = "h2")
public class H2AdoptionRepository implements AdoptionRepository {

    private final EntityManager entityManager;
    private final SearchSession searchSession;
    private final SearchMapping searchMapping;

    public H2AdoptionRepository(final EntityManager entityManager, final SearchSession searchSession, final SearchMapping searchMapping) {
        this.entityManager = entityManager;
        this.searchSession = searchSession;
        this.searchMapping = searchMapping;
    }

    @Override
    public Multi<Monster> getAllMonsters() {
        final List<MonsterEntity> monsters = entityManager
                .createQuery("SELECT monster from MonsterEntity monster", MonsterEntity.class)
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
    public Multi<Monster> searchMonstersByName(final String pattern, final Optional<Integer> size) {
        return Multi.createFrom().iterable(searchSession.search(MonsterEntity.class)
                .where(f ->
                        null == pattern || pattern.trim().isEmpty() ?
                                f.matchAll() :
                                f.simpleQueryString()
                                        .fields("name").matching(pattern)
                )
                .fetchHits(size.orElse(20))
                .stream()
                .map(MonsterEntity::toModel)
                .collect(Collectors.toList())
        );
    }

    @Transactional
    @Override
    public Multi<Monster> searchMonstersByDescription(final String pattern,
                                                      final Optional<Integer> size) {
        return Multi.createFrom().iterable(searchSession.search(MonsterEntity.class)
                .where(f ->
                        null == pattern || pattern.trim().isEmpty() ?
                                f.matchAll() :
                                f.simpleQueryString()
                                        .fields("description").matching(pattern)
                )
                .fetchHits(size.orElse(20))
                .stream()
                .map(MonsterEntity::toModel)
                .collect(Collectors.toList())
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
                });
    }

    void onStart(@Observes final StartupEvent ev) throws InterruptedException {
        // only reindex if we imported some content
        if (0 < count()) {
            searchMapping.scope(Object.class)
                    .massIndexer()
                    .startAndWait();
        }
    }

    public Long count() {
        return (Long) entityManager.createQuery("SELECT COUNT(t) FROM MonsterEntity t")
                .getSingleResult();
    }
}
