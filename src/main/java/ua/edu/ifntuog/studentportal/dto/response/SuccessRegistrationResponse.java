package ua.edu.ifntuog.studentportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.edu.ifntuog.studentportal.enums.RoleType;

import java.util.Set;

@Data
@AllArgsConstructor
public class SuccessRegistrationResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleType> roles;
}
