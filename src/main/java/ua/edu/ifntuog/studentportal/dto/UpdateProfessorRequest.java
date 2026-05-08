package ua.edu.ifntuog.studentportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateProfessorRequest extends UpdateUserRequest {
    private Long departmentId;
    private String academicTitle;
}
