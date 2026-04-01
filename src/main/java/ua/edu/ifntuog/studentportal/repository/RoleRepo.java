package ua.edu.ifntuog.studentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.ifntuog.studentportal.entity.Role;
import ua.edu.ifntuog.studentportal.enums.RoleType;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);
}
