package school.hei.haapi.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import school.hei.haapi.SentryConf;
import school.hei.haapi.endpoint.rest.api.TeachingApi;
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.model.Course;
import school.hei.haapi.endpoint.rest.model.Group;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.TestUtils;

import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = GroupIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class CourseIT {
    @MockBean
    private SentryConf sentryConf;

    @MockBean
    private CognitoComponent cognitoComponentMock;

    private static ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, GroupIT.ContextInitializer.SERVER_PORT);
    }

    public static Course course1() {
        Course course = new Course();
        course.setId("course1_id");
        course.setName("Name of course one");
        course.setRef("CRS21001");
        course.setCredits(1);
        course.setTotalHours(1);
        return course;
    }

    public static Course course2() {
        Course course = new Course();
        course.setId("course2_id");
        course.setName("Name of course two");
        course.setRef("CRS21002");
        course.setCredits(2);
        course.setTotalHours(2);
        return course;
    }

    public static Course someCreatableCourse() {
        Course course = new Course();
        course.setName("Some name");
        course.setRef("GRP21-" + randomUUID());
        course.setCredits(3);
        course.setTotalHours(3);
        return course;
    }

    @BeforeEach
    public void setUp() {
        setUpCognito(cognitoComponentMock);
    }

    @Test
    void badtoken_read_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);

        TeachingApi api = new TeachingApi(anonymousClient);
        assertThrowsForbiddenException(api::getCourses);
    }

    @Test
    void student_read_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);

        TeachingApi api = new TeachingApi(student1Client);
        Course actual1 = api.getCourseById(COURSE1_ID);
        List<Course> actualCourses = api.getCourses();


        assertEquals(course1(), actual1);
        assertTrue(actualCourses.contains(course1()));
        assertTrue(actualCourses.contains(course2()));
    }
}
