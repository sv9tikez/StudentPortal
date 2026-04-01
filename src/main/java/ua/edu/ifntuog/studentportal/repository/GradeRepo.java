package ua.edu.ifntuog.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.ifntuog.studentportal.entity.Grade;

import java.util.List;

@Repository
public interface GradeRepo extends JpaRepository<Grade, Long> {

    List<Grade> findAllByStudentId(Long studentId);

    List<Grade> findAllByCourseId(Long courseId);

    List<Grade> findAllByStudentIdAndCourseId(Long studentId, Long courseId);
}
