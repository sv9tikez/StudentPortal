package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.request.DepartmentRequest;
import ua.edu.ifntuog.studentportal.dto.response.DepartmentResponse;
import ua.edu.ifntuog.studentportal.dto.request.UpdateDepartmentRequest;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse create(DepartmentRequest department);

    DepartmentResponse findById(Long id);

    DepartmentResponse findByName(String name);

    List<DepartmentResponse> findAll();

    List<DepartmentResponse> findAllByFacultyId(Long facultyId);

    void update(Long id, UpdateDepartmentRequest department);

    void delete(Long id);

    DepartmentResponse assignToFaculty(Long departmentId, Long facultyId);
}
