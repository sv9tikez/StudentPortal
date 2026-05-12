package ua.edu.ifntuog.studentportal.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateStudentRequest extends CreateUserRequest {

    private Long groupId;
}
