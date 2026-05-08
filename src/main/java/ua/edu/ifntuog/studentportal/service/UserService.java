package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.CreateUserRequest;
import ua.edu.ifntuog.studentportal.dto.UpdateUserRequest;
import ua.edu.ifntuog.studentportal.dto.UserResponse;
import ua.edu.ifntuog.studentportal.enums.RoleType;

import java.util.List;

public interface UserService {
    UserResponse save(CreateUserRequest dto);

    UserResponse findById(Long id);

    UserResponse findByEmail(String email);

    List<UserResponse> findAll();

    void update(Long id, UpdateUserRequest updated);

    void delete(Long id);

    void addRole(Long userId, RoleType roleType);

    void removeRole(Long userId, RoleType roleType);
}
