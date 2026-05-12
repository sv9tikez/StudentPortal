package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    StudentResponse save(Long userId, Long groupId);

    StudentResponse findById(Long id);

    List<StudentResponse> findAll();

    List<StudentResponse> findAllByGroupId(Long groupId);

    void delete(Long id);

    StudentResponse assignToGroup(Long studentId, Long groupId);
}
