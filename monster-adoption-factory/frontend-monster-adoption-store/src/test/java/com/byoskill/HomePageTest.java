package com.byoskill;


import com.byoskill.communication.client.CommunicationMessageClient;
import com.byoskill.communication.model.WelcomeMessage;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.smallrye.mutiny.Uni.createFrom;
import static org.mockito.Mockito.when;

@QuarkusTest
class HomePageTest {

    private static final String DEFAULT_WELCOME_MESSAGE = "Welcome to our website";


    @Inject
    @InjectMock
    @RestClient
    CommunicationMessageClient communicationMessageClient;

    @Test
    void testHelloEndpoint() {
        when(communicationMessageClient.getWelcomeMessage()).thenReturn(createFrom().item(new WelcomeMessage(DEFAULT_WELCOME_MESSAGE)));

        given()
                .when().get("/")
                .then()
                .statusCode(200)
                .body(Matchers.containsString(DEFAULT_WELCOME_MESSAGE));
    }

}