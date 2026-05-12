package ua.edu.ifntuog.studentportal.dto.response;

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
    private GradeType type;
    private LocalDate date;
    private StudentShortResponse student;
    private CourseShortResponse course;
}
