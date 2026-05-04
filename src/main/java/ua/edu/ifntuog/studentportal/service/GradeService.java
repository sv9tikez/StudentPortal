package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.entity.Grade;

import java.util.List;

public interface GradeService {
    Grade create(Grade grade);
    Grade getById(Long id);
    List<Grade> getAllByStudentId(Long studentId);
    List<Grade> getAllByCourseId(Long courseId);
    List<Grade> getAllByStudentIdAndCourseId(Long studentId, Long courseId);
    Grade update(Long id, Grade grade);
    void delete(Long id);
    Double getAverageGradeByStudentId(Long studentId);
}
