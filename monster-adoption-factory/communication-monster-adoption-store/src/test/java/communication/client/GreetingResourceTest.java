package communication.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.service.CommunicationService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {
    @Inject
    CommunicationService communicationService;

    @DisplayName("Test GET /motd")
    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/motd")
                .then()
                .statusCode(200)
                .body(Matchers.is(new MessageMatcher()));
    }

    private class MessageMatcher extends BaseMatcher<String> {
        @Override
        public boolean matches(Object jsonAnswer) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map map = objectMapper.readValue((String) jsonAnswer, Map.class);
                return Arrays.asList(communicationService.getMessages()).contains(map.get("motd"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void describeMismatch(Object actual, Description mismatchDescription) {
            mismatchDescription.appendText("The expected message is not present in the list of messages");
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Compare the expected message with the list of messages");
        }
    }
}