package adoption.domain;

import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;

@Decorator
public class AdoptionExampleDecorator implements AdoptionRepository {

    @Inject
    @Delegate
    AdoptionRepository delegate;

    @Override
    public List<Monster> getAllMonsters() {

        return delegate.getAllMonsters();
    }

    @Override
    public void addMonsterToAdopt(Monster monster) {
        if (monster.getName().isBlank()) throw new WebApplicationException("Monster name cannot be empty", 400);
        if (monster. getDescription().isBlank()) throw new WebApplicationException("Monster name cannot be empty", 400);
        delegate.addMonsterToAdopt(monster);
    }

    @Override
    public Monster getMonsterByUuid(String id) {
        return delegate.getMonsterByUuid(id);
    }

    @Override
    public List<Monster> searchMonstersByName(String name) {
        return delegate.searchMonstersByName(name);
    }

    @Override
    public void deleteMonsterByUuid(String id) {
        delegate.deleteMonsterByUuid(id);
    }

    @Override
    public Monster updateMonsterByUuid(String id, Monster monster) {
        return delegate.updateMonsterByUuid(id, monster);
    }
}
