package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.CourseRequest;
import ua.edu.ifntuog.studentportal.dto.CourseResponse;
import ua.edu.ifntuog.studentportal.dto.UpdateCourseRequest;
import ua.edu.ifntuog.studentportal.entity.Course;

import java.util.List;

public interface CourseService {
    CourseResponse create(CourseRequest course);

    CourseResponse findById(Long id);

    List<CourseResponse> findAll();

    List<CourseResponse> findAllByGroupId(Long groupId);

    List<CourseResponse> findAllByProfessorId(Long professorId);

    List<CourseResponse> findAllBySubjectId(Long subjectId);

    void update(Long id, UpdateCourseRequest course);

    void delete(Long id);
}
