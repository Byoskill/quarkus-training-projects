package com.byoskill.adoption.repository;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.AssertSubscriber;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@QuarkusTest
class AdoptionRepositoryTest {

    private static final Integer MONSTERS_IN_JSON = 10;

    @Inject
    AdoptionRepository monsterRepository;

    @Test
    void testIntegrationMonsterRepository() {
        final Uni<List<Monster>> allMonstersP = monsterRepository.getAllMonsters().collect().asList();
        final var subscriber = allMonstersP.subscribe().withSubscriber(UniAssertSubscriber.create());
        final var sut = subscriber.assertCompleted();

        final List<Monster> items = sut.getItem();
        Assertions.assertFalse(items.isEmpty(), "We should have some monsters");
        Assertions.assertTrue(items.stream().anyMatch(hasMonster("Dracula")), "Dracula is present");


    }

    private Predicate<? super Monster> hasMonster(final String string) {
        return (m) -> m.getName().equals(string);
    }

    @Test
    void testSearchMonsterByName() {
        final Uni<List<Monster>> monstersWithDraculaName = monsterRepository.searchMonstersByName("Dracula", Optional.of(10)).collect().asList();
        final var subscriber = monstersWithDraculaName.subscribe().withSubscriber(UniAssertSubscriber.create());
        final var sut = subscriber.assertCompleted();

        final List<Monster> items = sut.getItem();
        Assertions.assertFalse(items.isEmpty(), "We should have one monster");
        Assertions.assertTrue(items.stream().anyMatch(hasMonster("Dracula")), "Dracula is present");
    }


    @Test
    void testgetAnyMonsters() {
        final Multi<Monster> monsters = monsterRepository.getAllMonsters();
        final var subscriber = monsters.subscribe().withSubscriber(AssertSubscriber.create());
        final var sut = subscriber.awaitItems(1);

        final List<Monster> items = sut.getItems();
        Assertions.assertFalse(items.isEmpty(), "We should have one monster");
    }

}