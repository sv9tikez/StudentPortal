package ua.edu.ifntuog.studentportal.service;

import ua.edu.ifntuog.studentportal.dto.request.CreateProfessorRequest;
import ua.edu.ifntuog.studentportal.dto.request.CreateStudentRequest;
import ua.edu.ifntuog.studentportal.dto.response.SuccessRegistrationResponse;

public interface AdminService {
    SuccessRegistrationResponse registerStudent(CreateStudentRequest dto);
    SuccessRegistrationResponse registerProfessor(CreateProfessorRequest dto);
}
