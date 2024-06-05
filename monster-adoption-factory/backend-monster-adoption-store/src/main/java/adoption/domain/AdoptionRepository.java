package adoption.domain;


import io.smallrye.mutiny.Uni;

import java.util.List;


/**
 * This interface describe the list of methods available to interact with the adoption system.
 */
public interface AdoptionRepository {

    List<Monster> getAllMonsters();

    void addMonsterToAdopt(Monster monster);

    Monster getMonsterByUuid(String id);

    List<Monster> searchMonstersByName(String name);

    void deleteMonsterByUuid(String id);

    Monster updateMonsterByUuid(String id, Monster monster);
}

