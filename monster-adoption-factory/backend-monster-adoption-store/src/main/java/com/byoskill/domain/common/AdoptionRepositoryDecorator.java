package com.byoskill.domain.common;

import com.byoskill.adapters.adoptions.rabbitmq.RabbitMqAdoptionEventProducer;
import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.function.Function;

@Priority(10)
@Decorator
public class AdoptionRepositoryDecorator implements AdoptionRepository {

    @Inject
    @Delegate
    AdoptionRepository delegate;

    @Inject
    RabbitMqAdoptionEventProducer eventProducer;

    @Override
    public Multi<Monster> getAllMonsters() {
        return delegate.getAllMonsters();
    }

    @Override
    public Uni<Monster> addMonsterToAdopt(Monster monster) {

        Function<? super Monster, Uni<? extends Monster>> monsterUniFunction = monster1 ->{
            if (monster1 != null) {
                eventProducer.sendAdoptionAvailableEvent("Monster " + monster1.getName() + " is available for adoption");
            }
            return Uni.createFrom().item(monster1);
        };
        return delegate.addMonsterToAdopt(monster).flatMap(monsterUniFunction);
    }

    @Override
    public Uni<Monster> getMonsterByUuid(String id) {
        return getMonsterByUuid(id);
    }

    @Override
    public Multi<Monster> searchMonstersByName(String pattern, Optional<Integer> size) {
        return delegate.searchMonstersByName(pattern, size);
    }

    @Override
    public Multi<Monster> searchMonstersByDescription(String pattern, Optional<Integer> size) {
        return null;
    }

    @Override
    public void deleteMonsterByUuid(String id) {
        delegate.deleteMonsterByUuid(id);
    }

    @Override
    public Uni<Monster> updateMonsterByUUID(String id, Monster monster) {
        return delegate.updateMonsterByUUID(id, monster);
    }

    @Override
    public Multi<Monster> searchMonstersByAge(Integer age) {
        return delegate.searchMonstersByAge(age);
    }

    @Override
    public Uni<Monster> adoptMonster(String monsterId) {

        Function<? super Monster, Uni<? extends Monster>> onAdoption = monster1 ->{
            if (monster1 != null) {
                eventProducer.sendAdoptionAvailableEvent("Monster " + monster1.getName() + " has been adopted");
            }
            return Uni.createFrom().item(monster1);
        };
        return delegate.adoptMonster(monsterId)
                .flatMap(onAdoption);
    }

    @Override
    public Uni<Monster> changeName(Monster entityToBeUpdated, String newName) {
        return delegate.changeName(entityToBeUpdated, newName);
    }
}
