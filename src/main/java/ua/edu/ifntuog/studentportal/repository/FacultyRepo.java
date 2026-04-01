package ua.edu.ifntuog.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.ifntuog.studentportal.entity.Faculty;

import java.util.Optional;

@Repository
public interface FacultyRepo extends JpaRepository<Faculty, Long> {

    Optional<Faculty> findByName(String name);

    boolean existsByName(String name);
}
