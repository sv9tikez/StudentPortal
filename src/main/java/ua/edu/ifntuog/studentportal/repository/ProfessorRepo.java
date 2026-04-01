package ua.edu.ifntuog.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.ifntuog.studentportal.entity.Professor;

@Repository
public interface ProfessorRepo extends JpaRepository<Professor, Long> {
}
