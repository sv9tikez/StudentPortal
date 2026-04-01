package ua.edu.ifntuog.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.ifntuog.studentportal.entity.Course;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {

    List<Course> findAllByGroupId(Long groupId);

    List<Course> findAllByProfessorId(Long professorId);

    List<Course> findAllBySubjectId(Long subjectId);

}
