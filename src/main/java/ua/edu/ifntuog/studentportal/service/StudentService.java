package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.entity.Student;

import java.util.List;

public interface StudentService {
    Student create(Student student);
    Student getById(Long id);
    List<Student> getAll();
    List<Student> getAllByGroupId(Long groupId);
    Student update(Long id, Student student);
    void delete(Long id);
    Student assignToGroup(Long studentId, Long groupId);
}
