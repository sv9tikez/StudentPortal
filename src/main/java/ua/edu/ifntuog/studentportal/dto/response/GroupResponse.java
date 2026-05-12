package ua.edu.ifntuog.studentportal.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class GroupResponse {
    private Long id;
    private String name;
    private Integer year;
    private DepartmentShortResponse department;
}
