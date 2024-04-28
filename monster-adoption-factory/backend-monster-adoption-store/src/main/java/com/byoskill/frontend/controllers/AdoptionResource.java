package com.byoskill.frontend.controllers;

import com.byoskill.domain.adoption.model.Monster;
import com.byoskill.domain.adoption.repository.AdoptionRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Uni<MonsterView> getAllMonsters2() {
        return monsterRepository.getAllMonsters()
                .log("getAllMonsters")
                .collect()
                .asList()
                .map(MonsterView::new);
    }

    @GET
    @Path("/{id}")
    public Uni<Monster> getMonsterByUuid(final String id) {
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
    public Uni<MonsterView> searchMonstersByName(final String name) {
        return monsterRepository.searchMonstersByName(name).collect().asList().map(MonsterView::new);
    }

    @POST
    public Uni<Monster> createMonster(final Monster monster) {
        return monsterRepository.addMonsterToAdopt(monster);
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(204)
    public void deleteMonsterById(final String id) {
        monsterRepository.deleteMonsterById(id);
    }

    @PUT
    @Path("/{id}")
    public Uni<Monster> updateMonsterById(final String id, final Monster monster) {
        return monsterRepository.updateMonsterByUUID(id, monster);
    }

    @GET
    @Path("/search/age/{age}")
    public Uni<MonsterView> searchMonstersByAge(@DefaultValue("0") final String age) {
        return monsterRepository.searchMonstersByAge(Integer.parseInt(age)).collect().asList().map(MonsterView::new);
    }

    @POST
    @Path("/{id}/name")
    public Uni<Monster> changeName(@PathParam("id") final String id, @QueryParam("name") final String name) {
        return monsterRepository.getMonsterByUuid(id)
                .log("findByUuid")
                .onItem().ifNull().failWith(new WebApplicationException("Monster not found", 404))
                .call(monster -> monsterRepository.changeName(monster, name));
    }

}