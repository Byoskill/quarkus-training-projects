package adoption.controllers;

import adoption.client.AdoptionClient;
import adoption.dto.MonsterForm;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.net.URI;
import java.time.Duration;

@Path("/adoptions")
public class AdoptionController {
    @Inject
    Template adoptionForm;

    @Inject
    @RestClient
    AdoptionClient adoptionClient;

    @Blocking
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return adoptionForm.instance();
    }

    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> addMonster(
            @FormParam("name") String name,
            @FormParam("description") String description,
            @FormParam("price") Integer price,
            @FormParam("age") Integer age,
            @FormParam("location") String location) {

        MonsterForm monster = new MonsterForm();
        monster.setName(name);
        monster.setDescription(description);
        monster.setPrice(price);
        monster.setAge(age);
        monster.setLocation(location);
        monster.setImage_url("https://robohash.org/"+ name + ".png?set=set1");
        Uni<Response> item = Uni.createFrom().item(Response.seeOther(URI.create("/")).build());
        return adoptionClient.addMonster(monster)
                .ifNoItem().after(Duration.ofSeconds(1)).failWith(new WebApplicationException(500))
                .flatMap(r -> item);
    }
}
