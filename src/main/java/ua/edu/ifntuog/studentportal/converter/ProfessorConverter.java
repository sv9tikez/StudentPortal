package ua.edu.ifntuog.studentportal.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import ua.edu.ifntuog.studentportal.dto.response.DepartmentShortResponse;
import ua.edu.ifntuog.studentportal.dto.response.ProfessorResponse;
import ua.edu.ifntuog.studentportal.entity.Professor;
import ua.edu.ifntuog.studentportal.entity.Role;

import java.util.stream.Collectors;

@Component
public class ProfessorConverter implements Converter<Professor, ProfessorResponse> {
    @Override
    public ProfessorResponse convert(MappingContext<Professor, ProfessorResponse> context) {
        Professor s = context.getSource();
        return ProfessorResponse.builder()
                .id(s.getId())
                .firstName(s.getUser().getFirstName())
                .lastName(s.getUser().getLastName())
                .email(s.getUser().getEmail())
                .academicTitle(s.getAcademicTitle())
                .roles(s.getUser().getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .department(DepartmentShortResponse.builder()
                        .id(s.getDepartment().getId())
                        .name(s.getDepartment().getName())
                        .build())
                .build();
    }
}
