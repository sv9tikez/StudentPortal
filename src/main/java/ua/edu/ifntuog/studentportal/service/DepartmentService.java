package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.DepartmentRequest;
import ua.edu.ifntuog.studentportal.dto.DepartmentResponse;
import ua.edu.ifntuog.studentportal.dto.UpdateDepartmentRequest;

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
