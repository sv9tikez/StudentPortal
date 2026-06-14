package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ifntuog.studentportal.dto.request.DepartmentRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateDepartmentRequest;
import ua.edu.ifntuog.studentportal.dto.response.DepartmentResponse;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.entity.Faculty;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.FacultyRepo;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    private static final Long DEPARTMENT_ID = 1L;
    private static final Long FACULTY_ID = 4L;
    private static final String DEPARTMENT_NAME = "Інженерія програмного забезпечення";

    private DepartmentRequest request;
    private UpdateDepartmentRequest updateRequest;
    private Department department;
    private Faculty faculty;

    @Mock
    private DepartmentRepo departmentRepo;

    @Mock
    private FacultyRepo facultyRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        request = new DepartmentRequest();
        request.setName(DEPARTMENT_NAME);
        request.setFacultyId(FACULTY_ID);

        updateRequest = new UpdateDepartmentRequest("ІПЗ", 3L);

        faculty = new Faculty();
        faculty.setId(FACULTY_ID);

        department = new Department();
        department.setId(DEPARTMENT_ID);
    }

    @Test
    void create_success() {
        when(departmentRepo.existsByName(DEPARTMENT_NAME)).thenReturn(false);
        when(facultyRepo.findById(FACULTY_ID)).thenReturn(Optional.of(faculty));
        when(departmentRepo.save(any(Department.class))).thenReturn(department);
        when(modelMapper.map(department, DepartmentResponse.class)).thenReturn(new DepartmentResponse());

        DepartmentResponse response = departmentService.create(request);

        assertNotNull(response);
    }

    @Test
    void create_failure_whenDuplicate() {
        when(departmentRepo.existsByName(DEPARTMENT_NAME)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> departmentService.create(request));
    }

    @Test
    void findById_success() {
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));
        when(modelMapper.map(department, DepartmentResponse.class)).thenReturn(new DepartmentResponse());

        assertNotNull(departmentService.findById(DEPARTMENT_ID));
    }

    @Test
    void findById_failure_whenMissing() {
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> departmentService.findById(DEPARTMENT_ID));
    }

    @Test
    void findByName_success() {
        when(departmentRepo.findByName(DEPARTMENT_NAME)).thenReturn(Optional.of(department));
        when(modelMapper.map(department, DepartmentResponse.class)).thenReturn(new DepartmentResponse());

        assertNotNull(departmentService.findByName(DEPARTMENT_NAME));
    }

    @Test
    void findByName_failure_whenMissing() {
        when(departmentRepo.findByName(DEPARTMENT_NAME)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> departmentService.findByName(DEPARTMENT_NAME));
    }

    @Test
    void findAll_success() {
        when(departmentRepo.findAll()).thenReturn(List.of(new Department()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new DepartmentResponse(), new DepartmentResponse()));

        List<DepartmentResponse> results = departmentService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void findAll_failure_whenRepoFails() {
        when(departmentRepo.findAll()).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> departmentService.findAll());
    }

    @Test
    void findAllByFacultyId_success() {
        when(departmentRepo.findAllByFacultyId(FACULTY_ID)).thenReturn(List.of(new Department()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new DepartmentResponse()));

        List<DepartmentResponse> results = departmentService.findAllByFacultyId(FACULTY_ID);

        assertEquals(1, results.size());
    }

    @Test
    void findAllByFacultyId_failure_whenRepoFails() {
        when(departmentRepo.findAllByFacultyId(FACULTY_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> departmentService.findAllByFacultyId(FACULTY_ID));
    }

    @Test
    void update_success() {
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));
        when(facultyRepo.findById(3L)).thenReturn(Optional.of(new Faculty()));

        departmentService.update(DEPARTMENT_ID, updateRequest);

        verify(departmentRepo).save(department);
        assertEquals("ІПЗ", department.getName());
    }

    @Test
    void update_failure_whenMissing() {
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> departmentService.update(DEPARTMENT_ID, updateRequest));
    }

    @Test
    void delete_success() {
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));

        departmentService.delete(DEPARTMENT_ID);

        verify(departmentRepo).delete(department);
    }

    @Test
    void delete_failure_whenMissing() {
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> departmentService.delete(DEPARTMENT_ID));
    }

    @Test
    void assignToFaculty_success() {
        Faculty facultyAssignee = new Faculty();
        facultyAssignee.setId(3L);

        when(departmentRepo.findById(DEPARTMENT_ID))
                .thenReturn(Optional.of(department));
        when(facultyRepo.findById(3L))
                .thenReturn(Optional.of(facultyAssignee));
        when(departmentRepo.save(department))
                .thenReturn(department);
        when(modelMapper.map(department, DepartmentResponse.class))
                .thenReturn(new DepartmentResponse());

        DepartmentResponse response = departmentService.assignToFaculty(DEPARTMENT_ID, 3L);

        assertNotNull(response);
        assertEquals(3L, department.getFaculty().getId());
    }

    @Test
    void assignToFaculty_failure_whenMissingDepartment() {
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> departmentService.assignToFaculty(DEPARTMENT_ID, 3L));
    }
}
