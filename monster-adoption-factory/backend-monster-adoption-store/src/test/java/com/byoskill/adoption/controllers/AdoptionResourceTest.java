package com.byoskill.adoption.controllers;

import com.byoskill.domain.adoption.model.Monster;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class AdoptionResourceTest {

    @Test
    void testGetMonsters() {
        given()
                .when().get("/adoptions")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("monsters", new GreaterTOrEqualshanSizeMatcher(5));
    }

    @DisplayName("Essai de récupération d'un monstre mais son ID n'existe pas")
    @Test
    void testGetOneMonster_not_found() {
        given()
                .when().get("/adoptions/randomkekekf")
                .then()
                .statusCode(204);

    }

    @DisplayName("Essai de récupération d'un monstre, le monstre existe")
    @Test
    void testGetOneMonster_existing() {
        final var firstMonster = given().when()
                .get("/adoptions")
                .jsonPath().getList("monsters", Monster.class).get(0);

        given()
                .when().get("/adoptions/" + firstMonster.getMonsterUUID())
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .body("name", equalTo("Godzilla"));
    }

    @DisplayName("Recherche d'un monstre par son nom.")
    @Test
    void testSearchMonster_by_name() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .when().get("/adoptions/search/Dracula")
                .then()
                .statusCode(200)
                .body("monsters", Matchers.hasSize(1))
                .body("monsters[0].name", equalTo("Dracula"));
    }

    @DisplayName("Ajout d'un monstre pour l'adoption")
    @Test
    void testAddMonster_for_adoption() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body("""
                        {"name": "Windigo", "location": "Canada", "age": 100, "description": "", "price":"200"}
                        """)
                .when()
                .post("/adoptions")
                .then()
                .statusCode(200)
                .body("name", equalTo("Windigo"))
                .body("id", not(is("")));
    }

    @DisplayName("Suppression d'un monstre pour l'adoption")
    @Test
    void testDeletionMonster_for_adoption() {
        final var firstMonster = given().when()
                .get("/adoptions")
                .jsonPath().getList("monsters", Monster.class).get(0);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .when()
                .delete("/adoptions/" + firstMonster.getMonsterUUID())
                .then()
                .statusCode(204);
    }

    @DisplayName("Update du prix d'un monstre pour l'adoption")
    @Test
    void testUpdateMonster_price() {
        final var firstMonster = given().when()
                .get("/adoptions")
                .jsonPath().getList("monsters", Monster.class).get(0);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body("""
                        {"price": "300"}
                        """)
                .when()
                .put("/adoptions/" + firstMonster.getMonsterUUID())
                .then()
                .statusCode(200);

    }

    public static class GreaterTOrEqualshanSizeMatcher extends TypeSafeMatcher<List<?>> {

        private final int expectedSize;

        public GreaterTOrEqualshanSizeMatcher(final int expectedSize) {
            this.expectedSize = expectedSize;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("The number of items should be greater than " + expectedSize);
        }

        @Override
        protected boolean matchesSafely(final List<?> item) {
            System.out.println(item);
            return null != item && item.size() > expectedSize;
        }

    }

}