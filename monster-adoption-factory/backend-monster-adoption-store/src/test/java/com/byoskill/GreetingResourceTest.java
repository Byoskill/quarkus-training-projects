package com.byoskill;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/motd")
          .then()
             .statusCode(200)
             .body("message", equalTo("Bienvenue sur notre site d'adoption"));
    }

}