package ua.edu.ifntuog.studentportal.dto;

import lombok.*;
import ua.edu.ifntuog.studentportal.enums.GradeType;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class GradeResponse {
    private Long id;
    private Integer grade;
    GradeType type;
    LocalDate date;
    Long studentId;
    Long courseId;
}
