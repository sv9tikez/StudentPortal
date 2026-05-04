package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.CreateUserRequest;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;

import java.util.List;

public interface UserService {
    User create(CreateUserRequest dto);
    User getById(Long id);
    User getByEmail(String email);
    List<User> getAll();
    User update(Long id, User user);
    void delete(Long id);
    boolean existsByEmail(String email);
    boolean addRole(Long userId, RoleType roleType);
    boolean removeRole(Long userId, RoleType roleType);
}
