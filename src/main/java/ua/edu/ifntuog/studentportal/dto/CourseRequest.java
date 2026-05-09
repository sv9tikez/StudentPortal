package ua.edu.ifntuog.studentportal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequest {
    @NotNull
    private Integer year;

    @NotNull
    private Long subjectId;

    @NotNull
    private Long professorId;

    @NotNull
    private Long groupId;
}
