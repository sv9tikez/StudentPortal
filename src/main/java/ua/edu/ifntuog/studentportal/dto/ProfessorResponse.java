package ua.edu.ifntuog.studentportal.dto;

import lombok.*;
import ua.edu.ifntuog.studentportal.enums.RoleType;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ProfessorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String academicTitle;
    private Set<RoleType> roles;
    private String departmentName;
}
