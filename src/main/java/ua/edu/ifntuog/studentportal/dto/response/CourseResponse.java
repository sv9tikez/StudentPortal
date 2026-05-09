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
    Long id;
    Integer year;
    String subjectName;
    String professorFullName;
    String groupName;
}
