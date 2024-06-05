package adapters.memory;


import adoption.domain.AdoptionRepository;
import adoption.domain.Monster;

import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jboss.logging.Logger;

import java.util.*;
import java.util.stream.Collectors;


@DefaultBean
@ApplicationScoped
public class AdoptionMemoryRepository implements AdoptionRepository {
    private static int counter = 0;
    private final Logger logger;
    private List<Monster> monsters;

    @Inject
    public AdoptionMemoryRepository(Logger logger) {
        monsters = new ArrayList<>();
        this.logger = logger;
    }

    @Override
    public List<Monster> getAllMonsters() {
        return Collections.unmodifiableList(monsters);
    }

    public void addMonsterToAdopt(Monster monster) {
        logger.info("Adding monster to adoption list : " + monster.getName());
        monsters.add(monster);
        monster.setId(++counter);
        monster.setMonsterUUID(UUID.randomUUID().toString());

    }

    @Override
    public Monster getMonsterByUuid(String id) {
        return monsters.stream().filter(m -> Objects.equals(m.getMonsterUUID(), id)).findFirst().orElse(null);
    }

    @Override
    public List<Monster> searchMonstersByName(String name) {
        return monsters.stream()
                .filter(m -> m.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMonsterByUuid(String id) {
        monsters.removeIf(m -> m.getMonsterUUID().equals(id));
    }

    @Override
    public Monster updateMonsterByUuid(String uuid, Monster monster) {
        if (uuid == null || monster == null) {
            return null;
        }
        Monster monsterToUpdate = getMonsterByUuid(uuid);
        if (monsterToUpdate != null) {
            if (monster.getName() != null) monsterToUpdate.setName(monster.getName());
            if (monster.getAge() != null) monsterToUpdate.setAge(monster.getAge());
            if (monster.getDescription() != null) monsterToUpdate.setDescription(monster.getDescription());
            if (monster.getLocation() != null) monsterToUpdate.setLocation(monster.getLocation());
            if (monster.getPrice() != null) monsterToUpdate.setPrice(monster.getPrice());
        } else throw new IllegalArgumentException("Monster not found");
        return monsterToUpdate;
    }
}