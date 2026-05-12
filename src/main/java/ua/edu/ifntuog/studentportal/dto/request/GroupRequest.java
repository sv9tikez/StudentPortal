package ua.edu.ifntuog.studentportal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GroupRequest {
    @NotBlank
    private String name;

    @NotNull
    private Long departmentId;

}
