package ua.edu.ifntuog.studentportal.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ifntuog.studentportal.dto.request.CreateProfessorRequest;
import ua.edu.ifntuog.studentportal.dto.request.CreateStudentRequest;
import ua.edu.ifntuog.studentportal.dto.response.ProfessorResponse;
import ua.edu.ifntuog.studentportal.dto.response.StudentResponse;
import ua.edu.ifntuog.studentportal.dto.response.SuccessRegistrationResponse;
import ua.edu.ifntuog.studentportal.dto.response.UserResponse;
import ua.edu.ifntuog.studentportal.service.ProfessorService;
import ua.edu.ifntuog.studentportal.service.StudentService;
import ua.edu.ifntuog.studentportal.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    private static final Long STUDENT_ID = 1L;
    private static final Long PROFESSOR_ID = 2L;
    private static final Long GROUP_ID = 10L;
    private static final Long DEPARTMENT_ID = 3L;
    private static final String STUDENT_FIRST_NAME = "student_firstName";
    private static final String STUDENT_LAST_NAME = "student_lastName";
    private static final String PROFESSOR_FIRST_NAME = "professor_firstName";
    private static final String PROFESSOR_LAST_NAME = "professor_lastName";
    private static final String EMAIL_STUDENT = "student@example.com";
    private static final String EMAIL_PROFESSOR = "professor@example.com";
    private static final String ACADEMIC_TITLE = "Docent";

    private CreateStudentRequest studentRequest;
    private CreateProfessorRequest professorRequest;
    private UserResponse studentUserResponse;
    private UserResponse professorUserResponse;
    private StudentResponse studentResponse;
    private ProfessorResponse professorResponse;

    @Mock
    private UserService userService;

    @Mock
    private StudentService studentService;

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        studentRequest = new CreateStudentRequest();
        studentRequest.setGroupId(GROUP_ID);

        professorRequest = new CreateProfessorRequest();
        professorRequest.setDepartmentId(DEPARTMENT_ID);
        professorRequest.setAcademicTitle(ACADEMIC_TITLE);

        studentUserResponse = UserResponse.builder()
                .id(STUDENT_ID)
                .firstName(STUDENT_FIRST_NAME)
                .lastName(STUDENT_LAST_NAME)
                .email(EMAIL_STUDENT)
                .build();

        professorUserResponse = UserResponse.builder()
                .id(PROFESSOR_ID)
                .firstName(PROFESSOR_FIRST_NAME)
                .lastName(PROFESSOR_LAST_NAME)
                .email(EMAIL_PROFESSOR)
                .build();

        studentResponse = new StudentResponse();
        studentResponse.setId(STUDENT_ID);
        studentResponse.setFirstName(STUDENT_FIRST_NAME);
        studentResponse.setLastName(STUDENT_LAST_NAME);
        studentResponse.setEmail(EMAIL_STUDENT);

        professorResponse = new ProfessorResponse();
        professorResponse.setId(PROFESSOR_ID);
        professorResponse.setFirstName(PROFESSOR_FIRST_NAME);
        professorResponse.setLastName(PROFESSOR_LAST_NAME);
        professorResponse.setEmail(EMAIL_PROFESSOR);
    }

    @Test
    void registerStudent_success() {
        when(userService.save(studentRequest)).thenReturn(studentUserResponse);
        when(studentService.save(STUDENT_ID, GROUP_ID)).thenReturn(studentResponse);

        SuccessRegistrationResponse response = adminService.registerStudent(studentRequest);

        assertEquals(STUDENT_ID, response.getUserId());
        assertEquals(STUDENT_FIRST_NAME, response.getFirstName());
    }

    @Test
    void registerStudent_failure_whenUserServiceFails() {
        when(userService.save(studentRequest)).thenThrow(new RuntimeException("fail"));

        assertThrows(RuntimeException.class, () -> adminService.registerStudent(studentRequest));
    }

    @Test
    void registerProfessor_success() {
        when(userService.save(professorRequest)).thenReturn(professorUserResponse);
        when(professorService.save(PROFESSOR_ID, DEPARTMENT_ID, ACADEMIC_TITLE)).thenReturn(professorResponse);

        SuccessRegistrationResponse response = adminService.registerProfessor(professorRequest);

        assertEquals(PROFESSOR_ID, response.getUserId());
        assertEquals(PROFESSOR_FIRST_NAME, response.getFirstName());
    }

    @Test
    void registerProfessor_failure_whenUserServiceFails() {
        when(userService.save(professorRequest)).thenThrow(new RuntimeException("fail"));

        assertThrows(RuntimeException.class, () -> adminService.registerProfessor(professorRequest));
    }
}
