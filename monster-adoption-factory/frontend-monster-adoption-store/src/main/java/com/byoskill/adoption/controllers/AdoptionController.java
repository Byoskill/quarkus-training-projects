package com.byoskill.adoption.controllers;

import java.net.URI;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import com.byoskill.adoption.client.MonsterClient;
import com.byoskill.adoption.model.MonsterForm;

import io.quarkus.arc.log.LoggerName;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/adoptions")
public class AdoptionController {
    
    @LoggerName("com.byoskill.adoptions.controllers")
    Logger LOGGER; 

    @Inject
    @RestClient
    MonsterClient monsterClient;
    
    @Inject
    Template adoptionForm; 


    @Blocking
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return adoptionForm.instance();
    }
 
    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addMonster(
            @FormParam("name") String name,
            @FormParam("description") String description,
            @FormParam("price") Integer price,
            @FormParam("age") Integer age,
            @FormParam("location") String location,
            @Context UriInfo uriInfo) {

        LOGGER.info("Received new adoption request with the following details :" + name + ", " + description + ", " + price + ", " + age + ", " + location);                
        MonsterForm monster = new MonsterForm();
        monster.setName(name);
        monster.setDescription(description);
        monster.setPrice(price);
        monster.setAge(age);
        monster.setLocation(location);

        monsterClient.addMonster(monster);

        URI uri = uriInfo.getBaseUriBuilder().path("/").build();
        return Response.seeOther(uri).build();
    }
}