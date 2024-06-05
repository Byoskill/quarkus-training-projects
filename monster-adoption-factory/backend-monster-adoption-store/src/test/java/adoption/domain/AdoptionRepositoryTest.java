package adoption.domain;

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
    private static Predicate<? super Monster> hasMonster(final String string) {
        return (m) -> m.getName().equals(string);
    }


    @Inject
    AdoptionRepository monsterRepository;

    @Test
    void testIntegrationMonsterRepository() {
        final List<Monster> items = monsterRepository.getAllMonsters();
        Assertions.assertFalse(items.isEmpty(), "We should have some monsters");
        Assertions.assertTrue(items.stream().anyMatch(hasMonster("Dracula")), "Dracula is present");
    }


    @Test
    void testSearchMonsterByName() {
        final List<Monster> monstersWithDraculaName = monsterRepository.searchMonstersByName("Dracula");
        Assertions.assertFalse(monstersWithDraculaName.isEmpty(), "We should have one monster");
        Assertions.assertTrue(monstersWithDraculaName.stream().anyMatch(hasMonster("Dracula")), "Dracula is present");
    }


    @Test
    void testgetAnyMonsters() {
        final List<Monster> items = monsterRepository.getAllMonsters();
        Assertions.assertFalse(items.isEmpty(), "We should have one monster");
    }

}
