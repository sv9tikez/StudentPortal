package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.GradeResponse;
import ua.edu.ifntuog.studentportal.dto.UpdateGradeRequest;
import ua.edu.ifntuog.studentportal.dto.GradeRequest;

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
