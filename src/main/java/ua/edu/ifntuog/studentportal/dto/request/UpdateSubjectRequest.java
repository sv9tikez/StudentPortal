package ua.edu.ifntuog.studentportal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateSubjectRequest {

    private String name;

    private Integer credits;

    private Integer hours;

    private Long departmentId;
}
