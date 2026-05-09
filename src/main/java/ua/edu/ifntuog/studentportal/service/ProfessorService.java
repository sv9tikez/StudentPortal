package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.response.ProfessorResponse;
import ua.edu.ifntuog.studentportal.dto.request.UpdateProfessorRequest;

import java.util.List;

public interface ProfessorService {
    ProfessorResponse save(Long userId, Long departmentId, String academicTitle);

    ProfessorResponse findById(Long id);

    List<ProfessorResponse> findAll();

    List<ProfessorResponse> findAllByDepartmentId(Long departmentId);

    void update(Long id, UpdateProfessorRequest updated);

    void delete(Long id);

    ProfessorResponse assignToDepartment(Long professorId, Long departmentId);
}
