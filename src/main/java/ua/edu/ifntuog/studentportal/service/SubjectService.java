package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.entity.Subject;

import java.util.List;

public interface SubjectService {
    Subject create(Subject subject);
    Subject getById(Long id);
    List<Subject> getAll();
    List<Subject> getAllByDepartmentId(Long departmentId);
    Subject update(Long id, Subject subject);
    void delete(Long id);
}
