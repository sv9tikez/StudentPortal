package ua.edu.ifntuog.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.ifntuog.studentportal.entity.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long> {

    List<Subject> findAllByDepartmentId(Long departmentId);

    Optional<Subject> findByName(String name);

    boolean existsByName(String name);
}
