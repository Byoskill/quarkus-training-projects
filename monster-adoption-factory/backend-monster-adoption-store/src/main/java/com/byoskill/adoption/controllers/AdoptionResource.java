package com.byoskill.adoption.controllers;

import com.byoskill.adoption.model.Monster;
import com.byoskill.adoption.repository.MonsterRepository;


import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.jboss.resteasy.reactive.ResponseStatus;

@Path("/adoptions")
@Produces(MediaType.APPLICATION_JSON)
public class AdoptionResource {

    @Inject
    MonsterRepository monsterRepository;

    @GET
    public MonsterView getAllMonsters() {
        return new MonsterView(monsterRepository.getAllMonsters());
    }

    @GET
    @Path("/{id}")
    public Monster getMonsterByUuid(String id) {
        return monsterRepository.getMonsterByUuid(id);
    }

    @GET
    @Path("/search/{name}")
    public MonsterView searchMonstersByName(String name) {
        return new MonsterView(monsterRepository.searchMonstersByName(name));
    }

    @POST    
    public Monster createMonster(Monster monster) {
        monsterRepository.addMonsterToAdopt(monster);
        return monster;
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(204)
    public void deleteMonsterById(String id) {
        monsterRepository.deleteMonsterById(id);
    }

    @PUT
    @Path("/{id}")
    public Monster updateMonsterById(String id, Monster monster) {
        return monsterRepository.updateMonsterByUUID(id, monster);
    }
}