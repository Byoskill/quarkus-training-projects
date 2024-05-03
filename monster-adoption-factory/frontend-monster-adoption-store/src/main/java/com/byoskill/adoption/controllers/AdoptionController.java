package com.byoskill.adoption.controllers;

import com.byoskill.adoption.client.AdoptionClient;
import com.byoskill.adoption.model.MonsterForm;
import io.quarkus.arc.log.LoggerName;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.net.URI;

@Path("/adoptions")
public class AdoptionController {

    @LoggerName("com.byoskill.adoptions.controllers")
    Logger LOGGER;

    @Inject
    @RestClient
    AdoptionClient adoptionClient;

    @Inject
    Template adoptionForm;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> get() {
        return Uni.createFrom().item(() -> adoptionForm.instance());
    }

    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> addMonster(
            @FormParam("name") final String name,
            @FormParam("description") final String description,
            @FormParam("price") final Integer price,
            @FormParam("age") final Integer age,
            @FormParam("location") final String location,
            @Context final UriInfo uriInfo) {

        LOGGER.info("Received new adoption request with the following details :%s, %s, %d, %d, %s".formatted(name, description, price, age, location));

        return Uni.createFrom().item(() -> {
                    final URI uri = uriInfo.getBaseUriBuilder().path("/").build();
                    final MonsterForm monster = new MonsterForm();
                    monster.setName(name);
                    monster.setDescription(description);
                    monster.setPrice(price);
                    monster.setAge(age);
                    monster.setLocation(location);
                    return monster;
                })
                .chain(monster -> adoptionClient.addMonster(monster))
                .map(monster -> Response.seeOther(URI.create("/")).build());
    }
}