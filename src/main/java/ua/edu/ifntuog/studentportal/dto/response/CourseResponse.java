package ua.edu.ifntuog.studentportal.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class CourseResponse {
    private Long id;
    private Integer year;
    private SubjectShortResponse subject;
    private ProfessorShortResponse professor;
    private GroupShortResponse group;
}
