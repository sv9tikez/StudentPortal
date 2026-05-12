package ua.edu.ifntuog.studentportal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.edu.ifntuog.studentportal.dto.request.CreateUserRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateStudentRequest extends CreateUserRequest {

    private Long groupId;
}
