package ua.edu.ifntuog.studentportal.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateProfessorRequest extends CreateUserRequest {
    private Long departmentId;
    private String academicTitle;
}
