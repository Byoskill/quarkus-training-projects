package controllers;

import adoption.client.AdoptionClient;
import adoption.dto.MonsterDto;
import communication.client.CommunicationMessageClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.qute.Template;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Path("")
public class HomePage {

    @Inject
    Template index;

    @Inject
    @RestClient
    CommunicationMessageClient communicationMessageClient;

    @Inject
    @RestClient
    AdoptionClient adoptionClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<String> get() {
        Uni<List<MonsterDto>> allMonsters = adoptionClient.getAllMonsters()
                .log("monsters.promise")
                .onItem()
                .transform(mv -> mv.monsters());
        Uni<String> welcomeMessageData = communicationMessageClient.getWelcomeMessage()
                .log("motd.promise")
                .onItem()
                .transform(wm -> wm.motd());
        return index.instance()
                .data("monsters", allMonsters)
                .data("motd", welcomeMessageData)
                .createUni();
    }

    @Path("/sse/{clientid}")
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<MonsterDto> streamMonsters(@PathParam("clientid") String clientId) {

        List<MonsterDto> monsterDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MonsterDto m = new MonsterDto(i + "", "Monster " + i, "Ceci est un monstre", "https://robohash.org/nouveaumonstre.png", 200);
            monsterDtos.add(m);
        }

        return Multi.createFrom().iterable(monsterDtos)
                .filter(m -> m.name().contains(clientId))
                .onItem()
                .call(
                        i -> {
                            Duration delay = Duration.ofSeconds(1);
                            return Uni.createFrom().nullItem().onItem().delayIt().by(delay);
                        }
                );
    }
}
