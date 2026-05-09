package ua.edu.ifntuog.studentportal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FacultyRequest {
    @NotBlank
    String name;
}
