package ua.edu.ifntuog.studentportal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.edu.ifntuog.studentportal.dto.request.CreateUserRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateProfessorRequest extends CreateUserRequest {
    private Long departmentId;
    private String academicTitle;
}
