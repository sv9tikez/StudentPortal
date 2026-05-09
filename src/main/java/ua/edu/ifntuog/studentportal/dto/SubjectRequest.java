package ua.edu.ifntuog.studentportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectRequest {
    @NotBlank
    private String name;

    @NotNull
    private Integer credits;

    @NotNull
    private Integer hours;

    @NotNull
    private Long departmentId;
}
