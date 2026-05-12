package ua.edu.ifntuog.studentportal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ifntuog.studentportal.dto.CreateProfessorRequest;
import ua.edu.ifntuog.studentportal.dto.CreateStudentRequest;
import ua.edu.ifntuog.studentportal.dto.SuccessRegistrationResponse;
import ua.edu.ifntuog.studentportal.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final String BASE_URL = "/create";

    @PostMapping(BASE_URL + "/student")
    public ResponseEntity<SuccessRegistrationResponse> registerStudent(
            @Valid @RequestBody CreateStudentRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.registerStudent(dto));
    }

    @PostMapping(BASE_URL + "/professor")
    public ResponseEntity<SuccessRegistrationResponse> registerProfessor(
            @Valid @RequestBody CreateProfessorRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.registerProfessor(dto));
    }
}
