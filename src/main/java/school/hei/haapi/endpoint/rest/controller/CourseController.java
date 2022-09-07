package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.CourseMapper;
import school.hei.haapi.model.Course;
import school.hei.haapi.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/course")
public class CourseController {
    private CourseService courseService;
    private CourseMapper courseMapper;

    @GetMapping(value = "/{id}")
    public Course getCourseById(@PathVariable String id) {
        return courseMapper.toRest(courseService.getById(id));
    }

    @GetMapping(value = "")
    public List<Course> getCourses() {
        return courseService.getAll().stream()
                .map(courseMapper::toRest)
                .collect(Collectors.toUnmodifiableList());
    }

    @PutMapping(value = "")
    public List<Course> createOrUpdateCourses(@RequestBody List<Course> toWrite) {
        return courseService.saveAll(toWrite.stream()
                        .map(courseMapper::toDomain)
                        .collect(Collectors.toUnmodifiableList())
                ).stream()
                .map(courseMapper::toRest)
                .collect(Collectors.toUnmodifiableList());
    }
}
