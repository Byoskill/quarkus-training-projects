package com.byoskill.adoption.repository;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;

import java.util.List;
import java.util.function.Predicate;

import org.jdom2.IllegalAddException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.byoskill.adoption.model.Monster;

@QuarkusTest
public class MonsterRepositoryTest {

    private static final Integer MONSTERS_IN_JSON = 10;

    @Inject
    AdoptionRepository monsterRepository;

    @Test
    void tetIntegrationMonsterRepository() {
        Uni<List<Monster>> allMonstersP = monsterRepository.getAllMonsters().collect().asList();
        var subscriber = allMonstersP.subscribe().withSubscriber(UniAssertSubscriber.create());
        var sut = subscriber.assertCompleted();
        Assertions.assertTrue(sut.getItem().stream().anyMatch(hasMonster("Dracula")), "Dracula is present");
        
    }

    private Predicate<? super Monster> hasMonster(String string) {
        return (m) -> m.getName().equals(string);
    }

}