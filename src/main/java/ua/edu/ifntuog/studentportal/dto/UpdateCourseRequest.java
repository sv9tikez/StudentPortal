package ua.edu.ifntuog.studentportal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Normalized;

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
