package com.byoskill.adoption.controllers;

import com.byoskill.adoption.model.Monster;
import com.byoskill.adoption.repository.AdoptionRepository;

import io.smallrye.mutiny.Uni;
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
    AdoptionRepository monsterRepository;

    @GET
    public Uni<MonsterView> getAllMonsters() {

        return monsterRepository.getAllMonsters()
                .log("getAllMonsters")
                .collect()
                .asList()
                .map(MonsterView::new);
    }

    @GET
    @Path("/monsters")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Uni<MonsterView> getAllMonsters2() {
        return monsterRepository.getAllMonsters()
                .log("getAllMonsters")
                .collect()
                .asList()
                .map(MonsterView::new);
    }

    @GET
    @Path("/{id}")
    public Uni<Monster> getMonsterByUuid(String id) {
        return monsterRepository.getMonsterByUuid(id);
    }

    @GET
    @Path("/adoptions/available")
    public Uni<MonsterView> getAvailableMonsters() {
        return monsterRepository.getAllMonsters()
                .log("getAvailableMonsters")
                .collect()
                .asList()
                .map(MonsterView::new);
    }

    @GET
    @Path("/search/{name}")
    public Uni<MonsterView> searchMonstersByName(String name) {
        return monsterRepository.searchMonstersByName(name).collect().asList().map(MonsterView::new);
    }

    @POST
    public Uni<Monster> createMonster(Monster monster) {
        return monsterRepository.addMonsterToAdopt(monster);
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(204)
    public void deleteMonsterById(String id) {
        monsterRepository.deleteMonsterById(id);
    }

    @PUT
    @Path("/{id}")
    public Uni<Monster> updateMonsterById(String id, Monster monster) {
        return monsterRepository.updateMonsterByUUID(id, monster);
    }
}