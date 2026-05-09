package ua.edu.ifntuog.studentportal.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.edu.ifntuog.studentportal.enums.GradeType;

import java.time.LocalDate;

@Data
public class GradeRequest {
    @NotNull
    @Min(0)
    @Max(100)
    private Integer grade;

    @NotNull
    private GradeType type;

    @NotNull
    private LocalDate date;

    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;
}
