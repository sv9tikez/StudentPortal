package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ifntuog.studentportal.dto.request.UpdateProfessorRequest;
import ua.edu.ifntuog.studentportal.dto.response.ProfessorResponse;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.entity.Professor;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.ProfessorRepo;
import ua.edu.ifntuog.studentportal.repository.UserRepo;
import ua.edu.ifntuog.studentportal.service.UserService;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceImplTest {

    private static final Long USER_ID = 1L;
    private static final Long DEPARTMENT_ID = 10L;
    private static final String ACADEMIC_TITLE = "professor";

    private User user;
    private Department department;
    private Professor professor;
    private UpdateProfessorRequest updateRequest;

    @Mock
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ProfessorRepo professorRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProfessorServiceImpl professorService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(USER_ID);

        department = new Department();
        department.setId(DEPARTMENT_ID);

        professor = new Professor();
        professor.setId(USER_ID);

        updateRequest = new UpdateProfessorRequest("Docent");
    }

    @Test
    void save_success() {
        when(professorRepo.existsById(USER_ID)).thenReturn(false);
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));
        when(professorRepo.save(any(Professor.class))).thenReturn(professor);
        when(modelMapper.map(professor, ProfessorResponse.class)).thenReturn(new ProfessorResponse());

        ProfessorResponse response = professorService.save(USER_ID, DEPARTMENT_ID, ACADEMIC_TITLE);

        assertNotNull(response);
        verify(userService).addRole(USER_ID, RoleType.ROLE_PROFESSOR);
    }

    @Test
    void save_failure_whenAlreadyExists() {
        when(professorRepo.existsById(USER_ID)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> professorService.save(USER_ID, DEPARTMENT_ID, ACADEMIC_TITLE));
        verify(professorRepo, never()).save(any(Professor.class));
    }

    @Test
    void findById_success() {
        when(professorRepo.findById(USER_ID)).thenReturn(Optional.of(professor));
        when(modelMapper.map(professor, ProfessorResponse.class)).thenReturn(new ProfessorResponse());

        assertNotNull(professorService.findById(USER_ID));
    }

    @Test
    void findById_failure_whenMissing() {
        when(professorRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> professorService.findById(USER_ID));
    }

    @Test
    void findAll_success() {
        when(professorRepo.findAll()).thenReturn(List.of(new Professor()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new ProfessorResponse()));

        List<ProfessorResponse> results = professorService.findAll();

        assertEquals(1, results.size());
    }

    @Test
    void findAll_failure_whenRepoFails() {
        when(professorRepo.findAll()).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> professorService.findAll());
    }

    @Test
    void findAllByDepartmentId_success() {
        when(professorRepo.findAllByDepartmentId(DEPARTMENT_ID)).thenReturn(List.of(new Professor()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new ProfessorResponse(), new ProfessorResponse()));

        List<ProfessorResponse> results = professorService.findAllByDepartmentId(DEPARTMENT_ID);

        assertEquals(2, results.size());
    }

    @Test
    void findAllByDepartmentId_failure_whenRepoFails() {
        when(professorRepo.findAllByDepartmentId(DEPARTMENT_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> professorService.findAllByDepartmentId(DEPARTMENT_ID));
    }

    @Test
    void update_success() {
        when(professorRepo.findById(USER_ID)).thenReturn(Optional.of(professor));

        professorService.update(USER_ID, updateRequest);

        verify(professorRepo).save(professor);
        assertEquals("Docent", professor.getAcademicTitle());
    }

    @Test
    void update_failure_whenMissing() {
        when(professorRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> professorService.update(USER_ID, updateRequest));
    }

    @Test
    void delete_success() {
        when(professorRepo.findById(USER_ID)).thenReturn(Optional.of(professor));

        professorService.delete(USER_ID);

        verify(professorRepo).delete(professor);
    }

    @Test
    void delete_failure_whenMissing() {
        when(professorRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> professorService.delete(USER_ID));
    }

    @Test
    void assignToDepartment_success() {
        when(professorRepo.findById(USER_ID)).thenReturn(Optional.of(professor));
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));
        when(professorRepo.save(professor)).thenReturn(professor);
        when(modelMapper.map(professor, ProfessorResponse.class)).thenReturn(new ProfessorResponse());

        ProfessorResponse response = professorService.assignToDepartment(USER_ID, DEPARTMENT_ID);

        assertNotNull(response);
        assertEquals(department, professor.getDepartment());
    }

    @Test
    void assignToDepartment_failure_whenMissingProfessor() {
        when(professorRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> professorService.assignToDepartment(USER_ID, DEPARTMENT_ID));
    }
}
