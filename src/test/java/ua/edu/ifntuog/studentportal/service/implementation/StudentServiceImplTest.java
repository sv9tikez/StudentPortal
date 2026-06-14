package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ifntuog.studentportal.dto.response.StudentResponse;
import ua.edu.ifntuog.studentportal.entity.Group;
import ua.edu.ifntuog.studentportal.entity.Student;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.GroupRepo;
import ua.edu.ifntuog.studentportal.repository.StudentRepo;
import ua.edu.ifntuog.studentportal.repository.UserRepo;
import ua.edu.ifntuog.studentportal.service.UserService;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    private static final Long USER_ID = 1L;
    private static final Long GROUP_ID = 2L;

    private User user;
    private Group group;
    private Student student;

    @Mock
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private GroupRepo groupRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(USER_ID);

        group = new Group();
        group.setId(GROUP_ID);

        student = new Student();
        student.setId(USER_ID);
        student.setUser(user);
    }

    @Test
    void save_success() {
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));
        when(studentRepo.existsById(USER_ID)).thenReturn(false);
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.of(group));
        when(studentRepo.save(any(Student.class))).thenReturn(student);
        when(modelMapper.map(student, StudentResponse.class)).thenReturn(new StudentResponse());

        StudentResponse response = studentService.save(USER_ID, GROUP_ID);

        assertNotNull(response);
        verify(userService).addRole(USER_ID, RoleType.ROLE_STUDENT);
        verify(studentRepo).save(any(Student.class));
    }

    @Test
    void save_failure_whenAlreadyExists() {
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));
        when(studentRepo.existsById(USER_ID)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> studentService.save(USER_ID, GROUP_ID));
        verify(studentRepo, never()).save(any(Student.class));
    }

    @Test
    void findById_success() {
        when(studentRepo.findById(USER_ID)).thenReturn(Optional.of(student));
        when(modelMapper.map(student, StudentResponse.class)).thenReturn(new StudentResponse());

        assertNotNull(studentService.findById(USER_ID));
    }

    @Test
    void findById_failure_whenMissing() {
        when(studentRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.findById(USER_ID));
    }

    @Test
    void findAll_success() {
        when(studentRepo.findAll()).thenReturn(List.of(new Student()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new StudentResponse(), new StudentResponse()));

        List<StudentResponse> results = studentService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void findAll_failure_whenRepoFails() {
        when(studentRepo.findAll()).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> studentService.findAll());
    }

    @Test
    void findAllByGroupId_success() {
        when(studentRepo.findAllByGroupId(GROUP_ID)).thenReturn(List.of(new Student()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new StudentResponse()));

        List<StudentResponse> results = studentService.findAllByGroupId(GROUP_ID);

        assertEquals(1, results.size());
    }

    @Test
    void findAllByGroupId_failure_whenRepoFails() {
        when(studentRepo.findAllByGroupId(GROUP_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> studentService.findAllByGroupId(GROUP_ID));
    }

    @Test
    void delete_success() {
        when(studentRepo.findById(USER_ID)).thenReturn(Optional.of(student));

        studentService.delete(USER_ID);

        verify(studentRepo).delete(student);
    }

    @Test
    void delete_failure_whenMissing() {
        when(studentRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.delete(USER_ID));
    }

    @Test
    void assignToGroup_success() {
        when(studentRepo.findById(USER_ID)).thenReturn(Optional.of(student));
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.of(group));
        when(studentRepo.save(student)).thenReturn(student);
        when(modelMapper.map(student, StudentResponse.class)).thenReturn(new StudentResponse());

        StudentResponse response = studentService.assignToGroup(USER_ID, GROUP_ID);

        assertNotNull(response);
        assertEquals(group, student.getGroup());
    }

    @Test
    void assignToGroup_failure_whenStudentMissing() {
        when(studentRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.assignToGroup(USER_ID, GROUP_ID));
    }
}
