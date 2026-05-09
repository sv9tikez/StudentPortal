package ua.edu.ifntuog.studentportal.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import ua.edu.ifntuog.studentportal.enums.GradeType;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateGradeRequest {
    @Min(0)
    @Max(100)
    private Integer grade;

    private GradeType type;

    private LocalDate date;

    private Long studentId;

    private Long courseId;
}
