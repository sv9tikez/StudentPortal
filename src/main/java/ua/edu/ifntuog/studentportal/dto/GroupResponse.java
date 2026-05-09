package ua.edu.ifntuog.studentportal.dto;

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
    private String departmentName;
}
