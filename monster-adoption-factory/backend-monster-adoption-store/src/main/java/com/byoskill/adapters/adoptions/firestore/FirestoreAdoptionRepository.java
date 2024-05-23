package com.byoskill.adapters.adoptions.firestore;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import io.quarkus.arc.profile.IfBuildProfile;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@IfBuildProfile(anyOf = "firestore")
public class FirestoreAdoptionRepository implements AdoptionRepository {

    private static long ID;
    private final Firestore firestore;

    public FirestoreAdoptionRepository(final Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Multi<Monster> getAllMonsters() {
        final CollectionReference monsters = firestore.collection("monsters");
        final ApiFuture<QuerySnapshot> apiFuture = monsters.get();
        try {
            return Multi.createFrom().items(apiFuture.get().getDocuments().stream().map(snapshot -> snapshot.toObject(Monster.class)));
        } catch (final InterruptedException | ExecutionException e) {
            throw new WebApplicationException(e);
        }
    }

    @Override
    public Uni<Monster> addMonsterToAdopt(final Monster monster) {
        final CollectionReference monsters = firestore.collection("monsters");
        final List<ApiFuture<WriteResult>> futures = new ArrayList<>();
        monster.setMonsterUUID(UUID.randomUUID().toString());
        monster.setId(ID);
        ID++;
        futures.add(monsters.document(monster.getMonsterUUID()).set(monster));
        try {
            ApiFutures.allAsList(futures).get();
        } catch (final InterruptedException | ExecutionException e) {
            throw new WebApplicationException(e);
        }
        return Uni.createFrom().item(monster);
    }

    @Override
    public Uni<Monster> getMonsterByUuid(final String id) {
        final Query monsters = firestore.collection("monsters").whereEqualTo("monsterUUID", id).limit(1);
        final ApiFuture<QuerySnapshot> apiFuture = monsters.get();
        try {
            final List<QueryDocumentSnapshot> documents = apiFuture.get().getDocuments();
            if (documents.isEmpty()) {
                return Uni.createFrom().nullItem();
            }
            return Uni.createFrom().item(documents.get(0).toObject(Monster.class));
        } catch (final InterruptedException | ExecutionException e) {
            throw new WebApplicationException(e);
        }
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
        final CollectionReference monsters = firestore.collection("monsters");
        final Uni<Monster> monsterByUuid = getMonsterByUuid(id);
        return monsterByUuid.flatMap(m -> {
            final ApiFuture<WriteResult> entityToBeUpdated = monsters.document(m.getMonsterUUID()).set(monster);
            try {
                entityToBeUpdated.get();
                return getMonsterByUuid(id);
            } catch (final InterruptedException | ExecutionException e) {
                throw new WebApplicationException(e);
            }
        });
    }

    @Override
    public Multi<Monster> searchMonstersByAge(final Integer age) {
        final Query monsters = firestore.collection("monsters").whereEqualTo("age", age).limit(1);
        final ApiFuture<QuerySnapshot> apiFuture = monsters.get();
        try {
            final List<QueryDocumentSnapshot> documents = apiFuture.get().getDocuments();
            if (documents.isEmpty()) {
                return Multi.createFrom().empty();
            }
            return Multi.createFrom().items(documents.stream().map(d -> d.toObject(Monster.class)));
        } catch (final InterruptedException | ExecutionException e) {
            throw new WebApplicationException(e);
        }
    }

    @Override
    public Uni<Monster> adoptMonster(String monsterId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Uni<Monster> changeName(final Monster entityToBeUpdated, final String newName) {
        final CollectionReference monsters = firestore.collection("monsters");
        final ApiFuture<WriteResult> updated = monsters.document(entityToBeUpdated.getMonsterUUID()).update("name", newName);
        try {
            updated.get();
            return getMonsterByUuid(entityToBeUpdated.getMonsterUUID());
        } catch (final InterruptedException | ExecutionException e) {
            throw new WebApplicationException(e);
        }
    }
}
