package adapters.firestore;

import adoption.domain.AdoptionRepository;
import adoption.domain.Monster;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Vetoed;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Vetoed
//@Default
public class FirestoreAdoptionRepository implements AdoptionRepository {

    public static final String COLLECTION_NAME = "monsters";
    @Inject
    Firestore firestore; // Inject Firestore

    @Override
    public List<Monster> getAllMonsters() {
        final QuerySnapshot monsters;
        try {
            monsters = firestore.collection(COLLECTION_NAME).get().get();
            return monsters.toObjects(Monster.class);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addMonsterToAdopt(Monster monster) {
        final CollectionReference monsters = firestore.collection(COLLECTION_NAME);
        final List<ApiFuture<WriteResult>> futures = new ArrayList<>();
        monster.setMonsterUUID(UUID.randomUUID().toString());
        futures.add(monsters.document(monster.getMonsterUUID()).set(monster));
        try {
            ApiFutures.allAsList(futures).get();
        } catch (final InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Monster getMonsterByUuid(String id) {
        return null;
    }

    @Override
    public List<Monster> searchMonstersByName(String name) {
        return List.of();
    }

    @Override
    public void deleteMonsterByUuid(String id) {

    }

    @Override
    public Monster updateMonsterByUuid(String id, Monster monster) {
        return null;
    }
}
