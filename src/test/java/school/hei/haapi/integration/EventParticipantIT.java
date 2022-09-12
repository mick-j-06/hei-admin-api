package school.hei.haapi.integration;

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
import school.hei.haapi.endpoint.rest.model.CreateEventParticipant;
import school.hei.haapi.endpoint.rest.model.Event;
import school.hei.haapi.endpoint.rest.model.EventParticipant;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import java.time.Instant;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.*;
import static school.hei.haapi.integration.conf.TestUtils.anAvailableRandomPort;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = EventParticipantIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class EventParticipantIT {
    @MockBean
    private SentryConf sentryConf;

    @MockBean
    private CognitoComponent cognitoComponentMock;

    private static ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, EventIT.ContextInitializer.SERVER_PORT);
    }

    public static EventParticipant eventParticipant1() {
        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setId(EVENT_PARTICIPANT1_ID);
        eventParticipant.setStatus(EventParticipant.StatusEnum.EXPECTED);
        eventParticipant.setUserParticipantId(STUDENT1_ID);
        eventParticipant.setEventId(EVENT1_ID);
        return eventParticipant;
    }

    public static CreateEventParticipant someCreatableEvent() {
        CreateEventParticipant createEventParticipant = new CreateEventParticipant();
        createEventParticipant.setUserParticipantId("student3_id");
        return createEventParticipant;
    }

    @BeforeEach
    public void setUp() {
        setUpCognito(cognitoComponentMock);
    }

    @Test
    void badtoken_read_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);
        EventApi api = new EventApi(anonymousClient);
        assertThrowsForbiddenException(() -> api.getEventParticipants("EXPECTED",null,null));
    }

    @Test
    void badtoken_write_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);

        EventApi api = new EventApi(anonymousClient);
        assertThrowsForbiddenException(() -> api.createEventEventParticipants(EVENT1_ID,List.of()));
    }

//    @Test
//    void student_read_ok() throws ApiException {
//        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
//
//        EventApi api = new EventApi(student1Client);
//        Event actual1 = api.getEventById(EVENT1_ID);
//        List<Event> actualEvents = api.getEvents(null);
//
//        assertEquals(event1(), actual1);
//        assertTrue(actualEvents.contains(event1()));
//        assertTrue(actualEvents.contains(event2()));
//    }

//    @Test
//    void student_write_ko() {
//        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
//
//        EventApi api = new EventApi(student1Client);
//        assertThrowsForbiddenException(() -> api.createOrUpdatePlaces(List.of()));
//    }

//    @Test
//    void teacher_write_ko() {
//        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
//
//        EventApi api = new EventApi(teacher1Client);
//        assertThrowsForbiddenException(() -> api.createOrUpdateEvents(List.of()));
//    }

//    @Test
//    void manager_write_create_ok() throws ApiException {
//        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
//        Event toCreate3 = someCreatableEvent();
//        Event toCreate4 = someCreatableEvent();
//
//        EventApi api = new EventApi(manager1Client);
//        List<Event> created = api.createOrUpdateEvents(List.of(toCreate3, toCreate4));
//
//        assertEquals(2, created.size());
//        assertTrue(isValidUUID(created.get(0).getId()));
//        toCreate3.setId(created.get(0).getId());
//        assertEquals(created.get(0), toCreate3);
//        //
//        assertTrue(isValidUUID(created.get(1).getId()));
//        toCreate4.setId(created.get(1).getId());
//        assertEquals(created.get(1), toCreate4);
//    }

//    @Test
//    void manager_write_update_ok() throws ApiException {
//        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
//        EventApi api = new EventApi(manager1Client);
//        List<Event> toUpdate = api.createOrUpdateEvents(List.of(
//                someCreatableEvent(),
//                someCreatableEvent()));
//        Event toUpdate0 = toUpdate.get(0);
//        toUpdate0.setName("A new name zero");
//        Event toUpdate1 = toUpdate.get(1);
//        toUpdate1.setName("A new name one");
//
//        List<Event> updated = api.createOrUpdateEvents(toUpdate);
//
//        assertEquals(2, updated.size());
//        assertTrue(updated.contains(toUpdate0));
//        assertTrue(updated.contains(toUpdate1));
//    }

    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}
