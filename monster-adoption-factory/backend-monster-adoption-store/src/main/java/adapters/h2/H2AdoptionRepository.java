package adapters.h2;


import adoption.domain.AdoptionRepository;
import adoption.domain.Monster;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@IfBuildProperty(name = "com.byoskill.adoptions.adapter", stringValue = "h2")
public class H2AdoptionRepository implements AdoptionRepository {

    private final EntityManager entityManager;

    @Inject
    public H2AdoptionRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override

    public List<Monster> getAllMonsters() {
        final List<MonsterEntity> monsters = entityManager
                .createQuery("SELECT monster from MonsterEntity monster", MonsterEntity.class)
                .getResultList();
        return monsters.stream().map(MonsterEntity::toModel).collect(Collectors.toUnmodifiableList());
    }


    @Transactional
    @Override
    public void addMonsterToAdopt(final Monster monster) {
        final MonsterEntity entity = MonsterEntity.fromModel(monster);
        entityManager.persist(entity);
    }


    @Override
    public Monster getMonsterByUuid(final String uuid) {
        final TypedQuery<MonsterEntity> query = entityManager.createQuery("SELECT monster from MonsterEntity monster where monster.monsterUUID = :id", MonsterEntity.class);
        query.setParameter("id", uuid);
        final List<MonsterEntity> resultList = query.getResultList();
        return 1 == resultList.size() ? resultList.get(0).toModel() : null;
    }


    @Override
    public List<Monster> searchMonstersByName(final String name) {
        final TypedQuery<MonsterEntity> query = entityManager.createQuery("SELECT monster from MonsterEntity monster where monster.name = :name", MonsterEntity.class);
        query.setParameter("name", name);
        final List<MonsterEntity> resultList = query.getResultList();
        return resultList.stream().map(MonsterEntity::toModel).collect(Collectors.toUnmodifiableList());
    }


    @Override
    public void deleteMonsterByUuid(final String uuid) {
        entityManager.createQuery("DELETE FROM MonsterEntity monster where monster.monsterUUID = :id");
        entityManager.flush();
    }
    @Override
    public Monster updateMonsterByUuid(final String uuid, final Monster monster) {
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

        return monster;
    }

}

