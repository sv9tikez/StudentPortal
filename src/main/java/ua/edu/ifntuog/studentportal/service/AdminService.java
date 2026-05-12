package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.CreateProfessorRequest;
import ua.edu.ifntuog.studentportal.dto.CreateStudentRequest;
import ua.edu.ifntuog.studentportal.dto.SuccessRegistrationResponse;

public interface AdminService {
    SuccessRegistrationResponse registerStudent(CreateStudentRequest dto);
    SuccessRegistrationResponse registerProfessor(CreateProfessorRequest dto);
}
