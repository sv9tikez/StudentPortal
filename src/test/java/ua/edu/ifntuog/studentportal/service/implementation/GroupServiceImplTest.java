package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ifntuog.studentportal.dto.request.GroupRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateGroupRequest;
import ua.edu.ifntuog.studentportal.dto.response.GroupResponse;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.entity.Group;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.GroupRepo;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    private static final Long GROUP_ID = 1L;
    private static final Long DEPARTMENT_ID = 3L;
    private static final String GROUP_NAME = "ІП-22-4";

    private GroupRequest request;
    private UpdateGroupRequest updateRequest;
    private Department department;
    private Group group;

    @Mock
    private GroupRepo groupRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GroupServiceImpl groupService;

    @BeforeEach
    void setUp() {
        request = new GroupRequest();
        request.setName(GROUP_NAME);
        request.setDepartmentId(DEPARTMENT_ID);

        updateRequest = new UpdateGroupRequest("ІПЗ-22-4", 4L);

        department = new Department();
        department.setId(DEPARTMENT_ID);

        group = new Group();
        group.setId(GROUP_ID);
    }

    @Test
    void create_success() {
        Group saved = new Group();
        saved.setId(GROUP_ID);

        when(groupRepo.existsByName(GROUP_NAME)).thenReturn(false);
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));
        when(groupRepo.save(any(Group.class))).thenReturn(saved);
        when(modelMapper.map(saved, GroupResponse.class)).thenReturn(new GroupResponse());

        GroupResponse response = groupService.create(request);

        assertNotNull(response);
        verify(groupRepo).save(any(Group.class));
    }

    @Test
    void create_failure_whenDuplicate() {
        when(groupRepo.existsByName(GROUP_NAME)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> groupService.create(request));
    }

    @Test
    void findById_success() {
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.of(group));
        when(modelMapper.map(group, GroupResponse.class)).thenReturn(new GroupResponse());

        assertNotNull(groupService.findById(GROUP_ID));
    }

    @Test
    void findById_failure_whenMissing() {
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> groupService.findById(GROUP_ID));
    }

    @Test
    void findAll_success() {
        when(groupRepo.findAll()).thenReturn(List.of(new Group()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new GroupResponse()));

        List<GroupResponse> results = groupService.findAll();

        assertEquals(1, results.size());
    }

    @Test
    void findAll_failure_whenRepoFails() {
        when(groupRepo.findAll()).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> groupService.findAll());
    }

    @Test
    void findAllByDepartmentId_success() {
        when(groupRepo.findAllByDepartmentId(DEPARTMENT_ID)).thenReturn(List.of(new Group()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new GroupResponse(), new GroupResponse()));

        List<GroupResponse> results = groupService.findAllByDepartmentId(DEPARTMENT_ID);

        assertEquals(2, results.size());
    }

    @Test
    void findAllByDepartmentId_failure_whenRepoFails() {
        when(groupRepo.findAllByDepartmentId(DEPARTMENT_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> groupService.findAllByDepartmentId(DEPARTMENT_ID));
    }

    @Test
    void update_success() {
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.of(group));
        when(departmentRepo.findById(4L)).thenReturn(Optional.of(new Department()));

        groupService.update(GROUP_ID, updateRequest);

        verify(groupRepo).save(group);
        assertEquals("ІПЗ-22-4", group.getName());
    }

    @Test
    void update_failure_whenMissing() {
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> groupService.update(GROUP_ID, updateRequest));
    }

    @Test
    void delete_success() {
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.of(group));

        groupService.delete(GROUP_ID);

        verify(groupRepo).delete(group);
    }

    @Test
    void delete_failure_whenMissing() {
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> groupService.delete(GROUP_ID));
    }
}
