package com.byoskill.adapters.adoptions.firestore;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import io.quarkus.arc.profile.IfBuildProfile;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@IfBuildProfile(anyOf = "firestore")
public class FirestoreAdoptionRepository implements AdoptionRepository {

    private final Firestore firestore;

    public FirestoreAdoptionRepository(final Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Multi<Monster> getAllMonsters() {
        return null;
    }

    @Override
    public Uni<Monster> addMonsterToAdopt(final Monster monster) {
        final CollectionReference persons = firestore.collection("monsters");
        final List<ApiFuture<WriteResult>> futures = new ArrayList<>();
        monster.setMonsterUUID(UUID.randomUUID().toString());
        futures.add(persons.document(monster.getMonsterUUID()).set(monster));
        try {
            ApiFutures.allAsList(futures).get();
        } catch (final InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return Uni.createFrom().item(monster);
    }

    @Override
    public Uni<Monster> getMonsterByUuid(final String id) {
        return null;
    }

    @Override
    public Multi<Monster> searchMonstersByName(final String pattern, final Optional<Integer> size) {
        return null;
    }

    @Override
    public Multi<Monster> searchMonstersByDescription(final String pattern, final Optional<Integer> size) {
        return null;
    }

    @Override
    public void deleteMonsterByUuid(final String id) {

    }

    @Override
    public Uni<Monster> updateMonsterByUUID(final String id, final Monster monster) {
        return null;
    }

    @Override
    public Multi<Monster> searchMonstersByAge(final Integer age) {
        return null;
    }

    @Override
    public Uni<Monster> changeName(final Monster entityToBeUpdated, final String newName) {
        return null;
    }
}
