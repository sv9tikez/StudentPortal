package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.entity.Group;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.GroupRepo;
import ua.edu.ifntuog.studentportal.service.GroupService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;

    @Override
    public Group create(Group group) {
        return groupRepo.save(group);
    }

    @Override
    public Group getById(Long id) {
        return groupRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found: " + id));
    }

    @Override
    public List<Group> getAll() {
        return groupRepo.findAll();
    }

    @Override
    public List<Group> getAllByDepartmentId(Long departmentId) {
        return groupRepo.findAllByDepartmentId(departmentId);
    }

    @Override
    @Transactional
    public Group update(Long id, Group updated) {
        Group group = getById(id);
        group.setName(updated.getName());
        group.setYear(updated.getYear());
        group.setDepartment(updated.getDepartment());
        return groupRepo.save(group);
    }

    @Override
    public void delete(Long id) {
        Group group = getById(id);
        groupRepo.delete(group);
    }
}
