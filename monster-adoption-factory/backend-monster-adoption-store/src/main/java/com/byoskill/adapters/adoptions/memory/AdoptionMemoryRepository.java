package com.byoskill.adapters.adoptions.memory;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import com.byoskill.frontend.utils.Logged;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class AdoptionMemoryRepository implements AdoptionRepository {
    private static long counter;
    private final List<Monster> monsters;

    public AdoptionMemoryRepository() {
        monsters = new ArrayList<>();
    }

    @Logged
    @Override
    public Multi<Monster> getAllMonsters() {
        return Multi.createFrom().iterable(monsters);
    }

    @Logged
    public Uni<Monster> addMonsterToAdopt(final Monster monster) {
        return Uni.createFrom().item(() -> {
            monsters.add(monster);
            ++counter;
            monster.setId(counter);
            monster.setMonsterUUID(UUID.randomUUID().toString());
            return monster;
        });

    }

    @Logged
    @Override
    public Uni<Monster> getMonsterByUuid(final String id) {
        return Uni.createFrom().item(
                monsters.stream()
                        .filter(m -> Objects.equals(m.getMonsterUUID(), id))
                        .findFirst().orElse(null));
    }

    @Logged
    @Override
    public Multi<Monster> searchMonstersByName(final String name) {
        return Multi.createFrom().items(monsters.stream().filter(m -> m.getName().contains(name)));
    }

    @Logged
    @Override
    public void deleteMonsterById(final String id) {
        monsters.removeIf(m -> m.getMonsterUUID().equals(id));
    }

    @Logged
    @Override
    public Uni<Monster> updateMonsterByUUID(final String uuid, final Monster monster) {
        if (null == uuid || null == monster) {
            return null;
        }
        final Uni<Monster> promise = getMonsterByUuid(uuid);
        return promise
                .log()
                // TIMEOUT
                .ifNoItem().after(Duration.ofMillis(500)).fail()
                .onItem()
                .transform(
                        monsterToUpdate -> {
                            if (null != monster.getName())
                                monsterToUpdate.setName(monster.getName());
                            if (null != monster.getAge())
                                monsterToUpdate.setAge(monster.getAge());
                            if (null != monster.getDescription())
                                monsterToUpdate.setDescription(monster.getDescription());
                            if (null != monster.getLocation())
                                monsterToUpdate.setLocation(monster.getLocation());
                            if (null != monster.getPrice())
                                monsterToUpdate.setPrice(monster.getPrice());
                            return monsterToUpdate;
                        });
    }

    @Logged
    @Override
    public Multi<Monster> searchMonstersByAge(final Integer age) {
        return Multi.createFrom().items(monsters.stream().filter(m -> Objects.equals(m.getAge(), age)));
    }

    @Override
    public Uni<Monster> changeName(final Monster monsterToBeUpdated, final String newName) {
        monsterToBeUpdated.setName(newName);
        return Uni.createFrom().item(monsterToBeUpdated);
    }
}