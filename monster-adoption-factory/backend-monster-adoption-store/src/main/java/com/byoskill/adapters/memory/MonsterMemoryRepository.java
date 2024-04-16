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
    public Monster getMonsterById(String id) {
        Integer k;
        try {
            k = Integer.parseInt(id);
        } catch (Exception e) {
            return null;
        }
        return monsters.stream().filter(m -> Objects.equals(m.getId(), k)).findFirst().orElse(null);
    }

    @Override
    public List<Monster> searchMonstersByName(String name) {        
      return monsters.stream().filter(m -> m.getName().contains(name)).collect(Collectors.toList());
    }

    @Override
    public void deleteMonsterById(String id) {
        monsters.removeIf(m -> m.getId().equals(id));
    }

    @Override
    public Monster updateMonsterById(String id, Monster monster) {
        if (id == null || monster == null) {
            return null;
        }
        Monster monsterToUpdate = getMonsterById(id);
        if (monsterToUpdate != null) {
            monsterToUpdate.setName(monster.getName());
            monsterToUpdate.setAge(monster.getAge());
            monsterToUpdate.setDescription(monster.getDescription());
            monsterToUpdate.setLocation(monster.getLocation());
            monsterToUpdate.setPrice(monster.getPrice());
        }
        return monsterToUpdate;
    }
}