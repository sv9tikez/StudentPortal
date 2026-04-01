package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.entity.Professor;

import java.util.List;

public interface ProfessorService {
    Professor create(Professor professor);
    Professor getById(Long id);
    List<Professor> getAll();
    List<Professor> getAllByDepartmentId(Long departmentId);
    Professor update(Long id, Professor professor);
    void delete(Long id);
    Professor assignToDepartment(Long professorId, Long departmentId);
}
