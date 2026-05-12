package ua.edu.ifntuog.studentportal.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class DepartmentResponse {
    private Long id;
    private String name;
    private FacultyResponse faculty;
}
