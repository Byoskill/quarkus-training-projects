package adoption.client;


import adoption.domain.AdoptionRepository;
import adoption.domain.Monster;
import app.AutorizationRequired;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Path("/adoptions")
@Tag(name = "adoption")
@Produces(MediaType.APPLICATION_JSON)
public class AdoptionResource {

    @Inject
    AdoptionRepository adoptionRepository;

    @Operation(
        summary = "Get all monsters",
        description = "Get all monsters without pagination"
    )

    /**@Retry(
        maxDuration = 1,
        durationUnit = ChronoUnit.SECONDS
    )**/
    @GET
    @Fallback(fallbackMethod = "getAllFallback")
    public MonsterView getAllMonsters() {
        if (ThreadLocalRandom.current().nextInt(3) == 0) {
            throw new WebApplicationException("Crash de l'API", 500);
        }
        return new MonsterView(adoptionRepository.getAllMonsters());
    }

    public MonsterView getAllFallback() {
        return new MonsterView(List.of());
    }

    @Operation(
            summary = "Find a monster by uuid",
            description = "Returns a monster by uuid"
    )
    @GET
    @Path("/{id}")
    public Monster getMonsterByUuid(String id) {
        return adoptionRepository.getMonsterByUuid(id);
    }

    @Operation(
            summary = "Search for a monster by name",
            description = "Search for a monster by name"
    )
    @GET
    @Path("/search/{name}")
    public MonsterView searchMonstersByName(String name) {
        return new MonsterView(adoptionRepository.searchMonstersByName(name));
    }

    @Operation(
            summary = "Submit a new adoption",
            description = "Submit a new adoption for a monster"
    )
    //@AutorizationRequired
    @Timeout(250)
    @POST
    @CircuitBreaker(requestVolumeThreshold = 4,skipOn = ConstraintViolationException.class)
    public Uni<Monster> createMonster(@Valid Monster monster) {
        adoptionRepository.addMonsterToAdopt(monster);
        return Uni.createFrom().item(monster);
    }

    @Operation(
            summary = "Remove a monster from adoption",
            description = "Remove a monster from adoption"
    )
    @DELETE
    @Path("/{id}")
    @ResponseStatus(204)
    @Transactional
    public void deleteMonsterById(String id) {
        adoptionRepository.deleteMonsterByUuid(id);
    }

    @Operation(
            summary = "Update a monster from adoption",
            description = "Update a monster from adoption"
    )
    @PUT
    @Path("/{id}")
    @Transactional
    public Monster updateMonsterById(String id, Monster monster) {
        return adoptionRepository.updateMonsterByUuid(id, monster);
    }
}