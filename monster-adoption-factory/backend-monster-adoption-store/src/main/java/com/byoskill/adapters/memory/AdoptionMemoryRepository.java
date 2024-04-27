package com.byoskill.adapters.memory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import com.byoskill.adoption.model.Monster;
import com.byoskill.adoption.repository.AdoptionRepository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdoptionMemoryRepository implements AdoptionRepository {
    private static int counter = 0;
    private List<Monster> monsters;

    public AdoptionMemoryRepository() {
        monsters = new ArrayList<>();
    }

    @Override
    public Multi<Monster> getAllMonsters() {
        return Multi.createFrom().iterable(monsters);
    }

    public Uni<Monster> addMonsterToAdopt(Monster monster) {
        return Uni.createFrom().item(() -> {
            monsters.add(monster);
            monster.setId(++counter);
            monster.setMonsterUUID(UUID.randomUUID().toString());
            return monster;
        });

    }

    @Override
    public Uni<Monster> getMonsterByUuid(String id) {
        return Uni.createFrom().item(
                monsters.stream()
                        .filter(m -> Objects.equals(m.getMonsterUUID(), id))
                        .findFirst().orElse(null));
    }

    @Override
    public Multi<Monster> searchMonstersByName(String name) {
        return Multi.createFrom().items(monsters.stream().filter(m -> m.getName().contains(name)));
    }

    @Override
    public void deleteMonsterById(String id) {
        monsters.removeIf(m -> m.getMonsterUUID().equals(id));
    }

    @Override
    public Uni<Monster> updateMonsterByUUID(String uuid, Monster monster) {
        if (uuid == null || monster == null) {
            return null;
        }
        Uni<Monster> promise = getMonsterByUuid(uuid);
        return promise
                .log()
                // TIMEOUT
                .ifNoItem().after(Duration.ofMillis(500)).fail()
                .onItem()                
                .transform(
                        monsterToUpdate -> {
                            if (monster.getName() != null)
                                monsterToUpdate.setName(monster.getName());
                            if (monster.getAge() != null)
                                monsterToUpdate.setAge(monster.getAge());
                            if (monster.getDescription() != null)
                                monsterToUpdate.setDescription(monster.getDescription());
                            if (monster.getLocation() != null)
                                monsterToUpdate.setLocation(monster.getLocation());
                            if (monster.getPrice() != null)
                                monsterToUpdate.setPrice(monster.getPrice());
                            return monsterToUpdate;
                        });
    }

    @Override
    public Multi<Monster> searchMonstersByAge(Integer age) {
        return Multi.createFrom().items(monsters.stream().filter(m -> Objects.equals(m.getAge(), age)));
    }
}