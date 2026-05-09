package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.GroupRequest;
import ua.edu.ifntuog.studentportal.dto.GroupResponse;
import ua.edu.ifntuog.studentportal.dto.UpdateGroupRequest;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.entity.Group;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.GroupRepo;
import ua.edu.ifntuog.studentportal.service.GroupService;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;
    private final DepartmentRepo departmentRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public GroupResponse create(GroupRequest dto) {

        if (groupRepo.existsByName(dto.getName())) {
            throw new EntityAlreadyExistsException("Group with name " + dto.getName() + " already exists");
        }

        Group group = new Group();
        group.setName(dto.getName());
        group.setYear(Year.now().getValue());
        group.setDepartment(findDepartmentById(dto.getDepartmentId()));
        return modelMapper.map(groupRepo.save(group), GroupResponse.class);
    }

    @Override
    public GroupResponse findById(Long id) {
        return modelMapper.map(findGroupById(id), GroupResponse.class);
    }

    @Override
    public List<GroupResponse> findAll() {
        return modelMapper.map(groupRepo.findAll(), new TypeToken<List<GroupResponse>>() {
        }.getType());
    }

    @Override
    public List<GroupResponse> findAllByDepartmentId(Long departmentId) {
        return modelMapper.map(groupRepo.findAllByDepartmentId(departmentId), new TypeToken<List<GroupResponse>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void update(Long id, UpdateGroupRequest updated) {
        Group group = findGroupById(id);
        group.setName(updated.getName());

        if (updated.getDepartmentId() != null) {
            group.setDepartment(findDepartmentById(updated.getDepartmentId()));
        }
        groupRepo.save(group);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Group group = findGroupById(id);
        groupRepo.delete(group);
    }

    private Group findGroupById(Long id) {
        return groupRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found: " + id));
    }

    private Department findDepartmentById(Long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
    }
}
