package com.byoskill.adoption.repository;

import java.util.List;

import com.byoskill.adoption.model.Monster;

/**
 * This interface describe the list of methods available to interact with the adoption system.
 */
public interface MonsterRepository {
    
    List<Monster> getAllMonsters();
    void addMonsterToAdopt(Monster monster);
    Monster getMonsterById(String id);
    List<Monster> searchMonstersByName(String name);
    void deleteMonsterById(String id);
    Monster updateMonsterById(String id, Monster monster);
}
