package ua.edu.ifntuog.studentportal.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import ua.edu.ifntuog.studentportal.dto.response.GroupShortResponse;
import ua.edu.ifntuog.studentportal.dto.response.StudentResponse;
import ua.edu.ifntuog.studentportal.entity.Role;
import ua.edu.ifntuog.studentportal.entity.Student;

import java.util.stream.Collectors;

@Component
public class StudentConverter implements Converter<Student, StudentResponse> {
    @Override
    public StudentResponse convert(MappingContext<Student, StudentResponse> context) {
        Student s = context.getSource();
        return StudentResponse.builder()
                .id(s.getId())
                .firstName(s.getUser().getFirstName())
                .lastName(s.getUser().getLastName())
                .email(s.getUser().getEmail())
                .roles(s.getUser().getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .group(GroupShortResponse.builder()
                        .id(s.getGroup().getId())
                        .name(s.getGroup().getName())
                        .build())
                .build();
    }
}
