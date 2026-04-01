package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.entity.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User getById(Long id);
    User getByEmail(String email);
    List<User> getAll();
    User update(Long id, User user);
    void delete(Long id);
    boolean existsByEmail(String email);
}
