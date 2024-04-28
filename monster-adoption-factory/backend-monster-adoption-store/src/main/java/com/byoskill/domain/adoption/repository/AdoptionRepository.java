package com.byoskill.domain.adoption.repository;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.common.model.ChangeNameOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * This interface describe the list of methods available to interact with the adoption system.
 */
public interface AdoptionRepository extends ChangeNameOperation<Monster> {

    Multi<Monster> getAllMonsters();

    Uni<Monster> addMonsterToAdopt(Monster monster);

    Uni<Monster> getMonsterByUuid(String id);

    Multi<Monster> searchMonstersByName(String name);

    void deleteMonsterById(String id);

    Uni<Monster> updateMonsterByUUID(String id, Monster monster);

    Multi<Monster> searchMonstersByAge(Integer age);
}
