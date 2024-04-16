package com.byoskill.adapters.memory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.byoskill.adoption.model.Monster;
import com.byoskill.adoption.repository.MonsterRepository;

import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
public class MonsterMemoryRepository implements MonsterRepository {
    private static int counter = 0;
    private List<Monster> monsters;

    public MonsterMemoryRepository() {
        monsters = new ArrayList<>();
    }

    @Override
    public List<Monster> getAllMonsters() {
        return Collections.unmodifiableList(monsters);
    }

    public void addMonsterToAdopt(Monster monster) {
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
      return monsters.stream().filter(m -> m.getName().contains(name)).collect(Collectors.toList());
    }

    @Override
    public void deleteMonsterById(String id) {
        monsters.removeIf(m -> m.getMonsterUUID().equals(id));
    }

    @Override
    public Monster updateMonsterByUUID(String uuid, Monster monster) {
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