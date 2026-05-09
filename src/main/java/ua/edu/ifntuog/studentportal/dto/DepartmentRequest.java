package ua.edu.ifntuog.studentportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartmentRequest {
    @NotBlank
    private String name;

    @NotNull
    private Long facultyId;
}
