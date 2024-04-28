package com.byoskill.adoption.repository;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

@QuarkusTest
public class MonsterRepositoryTest {

    private static final Integer MONSTERS_IN_JSON = 10;

    @Inject
    AdoptionRepository monsterRepository;

    @Test
    void tetIntegrationMonsterRepository() {
        final Uni<List<Monster>> allMonstersP = monsterRepository.getAllMonsters().collect().asList();
        final var subscriber = allMonstersP.subscribe().withSubscriber(UniAssertSubscriber.create());
        final var sut = subscriber.assertCompleted();
        Assertions.assertTrue(sut.getItem().stream().anyMatch(this.hasMonster("Dracula")), "Dracula is present");

    }

    private Predicate<? super Monster> hasMonster(final String string) {
        return (m) -> m.getName().equals(string);
    }

}