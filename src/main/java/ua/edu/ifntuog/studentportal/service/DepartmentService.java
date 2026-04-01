package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department create(Department department);
    Department getById(Long id);
    List<Department> getAll();
    List<Department> getAllByFacultyId(Long facultyId);
    Department update(Long id, Department department);
    void delete(Long id);
}
