package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.GroupRequest;
import ua.edu.ifntuog.studentportal.dto.GroupResponse;
import ua.edu.ifntuog.studentportal.dto.UpdateGroupRequest;

import java.util.List;

public interface GroupService {
    GroupResponse create(GroupRequest group);

    GroupResponse findById(Long id);

    List<GroupResponse> findAll();

    List<GroupResponse> findAllByDepartmentId(Long departmentId);

    void update(Long id, UpdateGroupRequest group);

    void delete(Long id);
}
