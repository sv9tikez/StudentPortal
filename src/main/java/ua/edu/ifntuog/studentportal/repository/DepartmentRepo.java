package ua.edu.ifntuog.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.ifntuog.studentportal.entity.Department;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

    List<Department> findAllByFacultyId(Long facultyId);

    Optional<Department> findByName(String name);

    boolean existsByName(String name);
}
