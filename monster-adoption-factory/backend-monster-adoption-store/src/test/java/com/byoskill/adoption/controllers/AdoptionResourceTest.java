package com.byoskill.adoption.controllers;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AdoptionResourceTest {

    @Test
    void testGetMonsters() {
        given()
                .when().get("/adoptions")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("monsters", Matchers.hasSize(10));
    }


    @DisplayName("Essai de récupération d'un monstre mais son ID n'existe pas")
    @Test
    void testGetOneMonster_not_found() {
        given()
                .when().get("/adoptions/randomkekekf")
                .then()
                .statusCode(204)
                .contentType(MediaType.APPLICATION_JSON);
                
    }

    @DisplayName("Essai de récupération d'un monstre, le monstre existe")
    @Test
    void testGetOneMonster_existing() {
        given()
                .when().get("/adoptions/1")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("name", Matchers.equalTo( "Gozilla"));
    }

}