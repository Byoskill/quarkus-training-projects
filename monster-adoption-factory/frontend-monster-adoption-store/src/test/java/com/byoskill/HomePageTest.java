package com.byoskill;



import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import static org.mockito.Mockito.when;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.byoskill.communication.client.CommunicationMessageService;
import com.byoskill.communication.model.WelcomeMessage;

import static io.restassured.RestAssured.given;

@QuarkusTest
class HomePageTest {
    
    public static final String DEFAULT_WELCOME_MESSAGE = "Welcome to our website";


    @InjectMock()
    @RestClient
    CommunicationMessageService communicationMessageService;

    @Test
    void testHelloEndpoint() {
        when(communicationMessageService.getWelcomeMessage()).thenReturn(new WelcomeMessage(DEFAULT_WELCOME_MESSAGE));

        given()
          .when().get("/")
          .then()
             .statusCode(200)
             .body(Matchers.containsString(DEFAULT_WELCOME_MESSAGE));
    }

}