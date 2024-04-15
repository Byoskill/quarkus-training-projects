package com.byoskill.adapters.memory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.byoskill.adoption.model.Monster;
import com.byoskill.adoption.repository.MonsterRepository;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.File;


@ApplicationScoped
public class MonsterMemoryRepository implements MonsterRepository {
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
    }
}