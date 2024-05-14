package com.byoskill.domain.adoption.repository;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.common.ChangeNameOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Optional;

/**
 * This interface describe the list of methods available to interact with the adoption system.
 */
public interface AdoptionRepository extends ChangeNameOperation<Monster> {

    Multi<Monster> getAllMonsters();

    Uni<Monster> addMonsterToAdopt(Monster monster);

    Uni<Monster> getMonsterByUuid(String id);

    Multi<Monster> searchMonstersByName(String pattern, Optional<Integer> size);

    Multi<Monster> searchMonstersByDescription(String pattern, Optional<Integer> size);
    
    void deleteMonsterByUuid(String id);

    Uni<Monster> updateMonsterByUUID(String id, Monster monster);

    Multi<Monster> searchMonstersByAge(Integer age);

}
