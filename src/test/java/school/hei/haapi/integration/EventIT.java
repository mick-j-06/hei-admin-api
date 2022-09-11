package school.hei.haapi.integration;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import school.hei.haapi.SentryConf;
import school.hei.haapi.endpoint.rest.api.EventApi;
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.model.Event;
import school.hei.haapi.endpoint.rest.model.Place;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = EventIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class EventIT {

    @MockBean
    private SentryConf sentryConf;

    @MockBean
    private CognitoComponent cognitoComponentMock;

    private static ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
    }

    public static Event event1() {
        Event event = new Event();
        event.setId("event1_id");
        event.setName("Name of event one");
        event.setRef("EVT21001");
        event.setStartingHours(Instant.parse("2022-09-11T12:00:00.00Z"));
        event.setEndingHours(Instant.parse("2022-09-11T14:00:00.00Z"));
        event.setUserManagerId("manager1_id");
        event.setPlaceId("place1_id");
        return event;
    }

    @BeforeEach
    public void setUp() {
        setUpCognito(cognitoComponentMock);
    }

    @Test
    void badtoken_read_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);
        EventApi api = new EventApi(anonymousClient);
        assertThrowsForbiddenException(() -> api.getEvents(PLACE1_ID));
    }

    @Test
    void badtoken_write_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);

        EventApi api = new EventApi(anonymousClient);
        assertThrowsForbiddenException(() -> api.createOrUpdateEvents(List.of()));
    }

    @Test
    void student_read_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);

        EventApi api = new EventApi(student1Client);
        Event actual1 = api.getEventById(EVENT1_ID);
        List<Event> actualEvents = api.getEvents(null);

        assertEquals(event1(), actual1);
        assertTrue(actualEvents.contains(event1()));
    }

    @Test
    void student_write_ko() {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);

        EventApi api = new EventApi(student1Client);
        assertThrowsForbiddenException(() -> api.createOrUpdatePlaces(List.of()));
    }

    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}
