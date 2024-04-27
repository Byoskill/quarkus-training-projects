package com.byoskill.adoption.repository;

import com.byoskill.adoption.model.Monster;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * This interface describe the list of methods available to interact with the adoption system.
 */
public interface AdoptionRepository {
    
    Multi<Monster> getAllMonsters();
    Uni<Monster> addMonsterToAdopt(Monster monster);
    Uni<Monster> getMonsterByUuid(String id);
    Multi<Monster> searchMonstersByName(String name);
    void deleteMonsterById(String id);
    Uni<Monster> updateMonsterByUUID(String id, Monster monster);
    Multi<Monster> searchMonstersByAge(Integer age);
}
