package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.response.GradeResponse;
import ua.edu.ifntuog.studentportal.dto.request.UpdateGradeRequest;
import ua.edu.ifntuog.studentportal.dto.request.GradeRequest;

import java.util.List;

public interface GradeService {
    GradeResponse create(GradeRequest grade);

    GradeResponse findById(Long id);

    List<GradeResponse> findAllByStudentId(Long studentId);

    List<GradeResponse> findAllByCourseId(Long courseId);

    List<GradeResponse> findAllByStudentIdAndCourseId(Long studentId, Long courseId);

    void update(Long id, UpdateGradeRequest grade);

    void delete(Long id);

    Double findAverageGradeByStudentId(Long studentId);
}
