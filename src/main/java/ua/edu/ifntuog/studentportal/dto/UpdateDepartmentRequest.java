package ua.edu.ifntuog.studentportal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDepartmentRequest {
    @NotBlank
    private String name;
    private Long facultyId;
}
