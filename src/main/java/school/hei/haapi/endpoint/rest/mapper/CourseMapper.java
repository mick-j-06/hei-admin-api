package school.hei.haapi.endpoint.rest.mapper;

import org.springframework.stereotype.Component;
import school.hei.haapi.model.Course;

@Component
public class CourseMapper {

    public Course toRest(Course course) {
        var restCourse = new Course();
        restCourse.setId(course.getId());
        restCourse.setRef(course.getRef());
        restCourse.setName(course.getName());
        restCourse.setCredits(course.getCredits());
        restCourse.setTotal_hours(course.getTotal_hours());
        return restCourse;
    }

    public Course toDomain(Course restCourse) {
        return Course.builder()
                .id(restCourse.getId())
                .ref(restCourse.getRef())
                .name(restCourse.getName())
                .credits(restCourse.getCredits())
                .total_hours(restCourse.getTotal_hours())
                .build();
    }
}
