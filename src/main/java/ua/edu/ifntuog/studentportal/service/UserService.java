package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.CreateUserRequest;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;

import java.util.List;

public interface UserService {
    User create(CreateUserRequest dto, RoleType roleType);
    User getById(Long id);
    User getByEmail(String email);
    List<User> getAll();
    User update(Long id, User user);
    void delete(Long id);
    boolean existsByEmail(String email);
}
