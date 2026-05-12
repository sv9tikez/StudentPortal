package ua.edu.ifntuog.studentportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ProfessorShortResponse {
    private Long id;
    private String firstName;
    private String lastName;
}