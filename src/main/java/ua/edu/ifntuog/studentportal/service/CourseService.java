package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.entity.Course;

import java.util.List;

public interface CourseService {
    Course create(Course course);
    Course getById(Long id);
    List<Course> getAll();
    List<Course> getAllByGroupId(Long groupId);
    List<Course> getAllByProfessorId(Long professorId);
    List<Course> getAllBySubjectId(Long subjectId);
    Course update(Long id, Course course);
    void delete(Long id);
}
