package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.entity.Group;

import java.util.List;

public interface GroupService {
    Group create(Group group);
    Group getById(Long id);
    List<Group> getAll();
    List<Group> getAllByDepartmentId(Long departmentId);
    Group update(Long id, Group group);
    void delete(Long id);
}
