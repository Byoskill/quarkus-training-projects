package communication.controllers;

import communication.client.CommunicationMessageClient;
import communication.dto.WelcomeMessage;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;

@QuarkusTest
class HomePageTest {

    public static final String DEFAULT_WELCOME_MESSAGE = "Welcome to our website";

    @InjectMock()
    @RestClient
    CommunicationMessageClient communicationMessageClient;

    @Test
    void testHelloEndpoint() {
        Mockito.when(communicationMessageClient.getWelcomeMessage())
                .thenReturn(Uni.createFrom().item(new WelcomeMessage(DEFAULT_WELCOME_MESSAGE)));

        given()
                .when().get("/")
                .then()
                .statusCode(200)
                .body(Matchers.anything());
    }

}