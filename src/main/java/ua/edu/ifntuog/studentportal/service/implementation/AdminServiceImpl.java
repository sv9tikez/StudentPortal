package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.*;
import ua.edu.ifntuog.studentportal.dto.response.ProfessorResponse;
import ua.edu.ifntuog.studentportal.dto.response.StudentResponse;
import ua.edu.ifntuog.studentportal.dto.response.UserResponse;
import ua.edu.ifntuog.studentportal.service.AdminService;
import ua.edu.ifntuog.studentportal.service.ProfessorService;
import ua.edu.ifntuog.studentportal.service.StudentService;
import ua.edu.ifntuog.studentportal.service.UserService;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final StudentService studentService;
    private final ProfessorService professorService;

    @Override
    @Transactional
    public SuccessRegistrationResponse registerStudent(CreateStudentRequest dto) {
        UserResponse user = userService.save(dto);
        StudentResponse student = studentService.save(user.getId(), dto.getGroupId());
        return new SuccessRegistrationResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getRoles());
    }

    @Override
    @Transactional
    public SuccessRegistrationResponse registerProfessor(CreateProfessorRequest dto) {
        UserResponse user = userService.save(dto);
        ProfessorResponse professor = professorService.save(user.getId(), dto.getDepartmentId(), dto.getAcademicTitle());
        return new SuccessRegistrationResponse(
                professor.getId(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getEmail(),
                professor.getRoles());
    }
}
