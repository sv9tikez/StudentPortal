package ua.edu.ifntuog.studentportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class StudentShortResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private GroupShortResponse group;
}
