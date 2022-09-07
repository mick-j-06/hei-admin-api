package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.Course;
import school.hei.haapi.repository.CourseRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private CourseRepository courseRepository;

    public Course getById(String courseId) {
        return courseRepository.getById(courseId);
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Transactional
    public List<Course> saveAll(List<Course> courseList) {
        return courseRepository.saveAll(courseList);
    }
}
