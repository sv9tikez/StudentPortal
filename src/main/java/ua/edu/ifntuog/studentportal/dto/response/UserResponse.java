package ua.edu.ifntuog.studentportal.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String[] roles;
}
