package com.byoskill.adoption.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.byoskill.adoption.model.Monster;

@QuarkusTest
public class MonsterRepositoryTest {

    private static final Integer MONSTERS_IN_JSON = 10;

    @Inject
    MonsterRepository monsterRepository; 

    @Test
    void tetIntegrationMonsterRepository() {
        Assertions.assertEquals(MONSTERS_IN_JSON, monsterRepository.getAllMonsters().size(), "No monster is loaded");
        Assertions.assertTrue(monsterRepository.getAllMonsters().stream().anyMatch(hasMonster("Dracula")), "Dracula is present" );
    }

    private Predicate<? super Monster> hasMonster(String string) {
        return (m) -> m.getName().equals(string);
    }

}