package ua.edu.ifntuog.studentportal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCourseRequest {
    private Integer year;
    private Long subjectId;
    private Long professorId;
    private Long groupId;
}
