package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.request.FacultyRequest;
import ua.edu.ifntuog.studentportal.dto.response.FacultyResponse;

import java.util.List;

public interface FacultyService {
    FacultyResponse create(FacultyRequest faculty);

    FacultyResponse findById(Long id);

    FacultyResponse findByName(String name);

    List<FacultyResponse> findAll();

    void update(Long id, FacultyRequest faculty);

    void delete(Long id);
}
