package adoption.client;

import adoption.domain.AdoptionRepository;
import adoption.domain.Monster;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static io.restassured.RestAssured.given;

@QuarkusTest
public class AdoptionResourceTest {

    @Inject
    private AdoptionRepository adoptionRepository;

    public static class GreaterOrEqualsThanSizeMatcher extends TypeSafeMatcher<List<?>> {

        private int expectedSize;

        public GreaterOrEqualsThanSizeMatcher(int expectedSize) {
            this.expectedSize = expectedSize;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("The unmber of items should be greater than " + expectedSize);
        }

        @Override
        protected boolean matchesSafely(List<?> item) {
            return item != null && item.size() > expectedSize;
        }

    }

    @Test
    void testGetMonsters() {
        given()
                .when().get("/adoptions")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("monsters", new GreaterOrEqualsThanSizeMatcher(10));
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
        Monster firstMonster = adoptionRepository.getAllMonsters().get(0);

        given()
                .when().get("/adoptions/" + firstMonster.getMonsterUUID())
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .body("name", equalTo( "Godzilla"));
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
        Monster firstMonster = adoptionRepository.getAllMonsters().get(0);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .when()
                .delete("/adoptions/" +  firstMonster.getMonsterUUID())
                .then()
                .statusCode(204);
    }

    @DisplayName("Update du prix d'un monstre pour l'adoption")
    @Test
    void testUpdateMonster_price() {
        Monster firstMonster = adoptionRepository.getAllMonsters().get(0);

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

}
