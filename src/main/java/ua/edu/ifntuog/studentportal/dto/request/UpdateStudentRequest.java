package ua.edu.ifntuog.studentportal.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStudentRequest extends UpdateUserRequest {

    private Long groupId;
}
