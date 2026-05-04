package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.entity.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty create(Faculty faculty);
    Faculty getById(Long id);
    Faculty getByName(String name);
    List<Faculty> getAll();
    Faculty update(Long id, Faculty faculty);
    void delete(Long id);
}
