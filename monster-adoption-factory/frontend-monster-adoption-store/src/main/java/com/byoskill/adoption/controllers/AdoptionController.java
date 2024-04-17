package com.byoskill.adoption.controllers;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.byoskill.adoption.model.MonsterForm;
import com.byoskill.communication.client.CommunicationMessageService;
import com.byoskill.communication.model.WelcomeMessage;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/adoptions")
public class AdoptionController {

    //@RestClient
    //CommunicationMessageService communicationMessageService;
    
    @Inject
    Template adoptionForm; 

    @Blocking
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        //WelcomeMessage welcomeMessage = communicationMessageService.getWelcomeMessage();
        return adoptionForm.instance();
    }
 @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addMonster(
            @FormParam("monsterUUID") String monsterUUID,
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

        monsterService.addMonster(monster);

        return Response.ok().build();
    }
}