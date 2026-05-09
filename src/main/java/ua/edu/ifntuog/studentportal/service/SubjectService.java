package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.SubjectRequest;
import ua.edu.ifntuog.studentportal.dto.SubjectResponse;
import ua.edu.ifntuog.studentportal.dto.UpdateSubjectRequest;

import java.util.List;

public interface SubjectService {
    SubjectResponse create(SubjectRequest subject);

    SubjectResponse findById(Long id);

    List<SubjectResponse> findAll();

    List<SubjectResponse> findAllByDepartmentId(Long departmentId);

    void update(Long id, UpdateSubjectRequest subject);

    void delete(Long id);
}
