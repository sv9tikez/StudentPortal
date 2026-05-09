package ua.edu.ifntuog.studentportal.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class SubjectResponse {
    private Long id;
    private String name;
    private Integer credits;
    private Integer hours;
    private String departmentName;
}
