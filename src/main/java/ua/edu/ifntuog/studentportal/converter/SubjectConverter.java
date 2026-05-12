package ua.edu.ifntuog.studentportal.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import ua.edu.ifntuog.studentportal.dto.response.DepartmentShortResponse;
import ua.edu.ifntuog.studentportal.dto.response.SubjectResponse;
import ua.edu.ifntuog.studentportal.entity.Subject;

@Component
public class SubjectConverter implements Converter<Subject, SubjectResponse> {
    @Override
    public SubjectResponse convert(MappingContext<Subject, SubjectResponse> context) {
        Subject s = context.getSource();
        return SubjectResponse.builder()
                .id(s.getId())
                .name(s.getName())
                .credits(s.getCredits())
                .hours(s.getHours())
                .department(DepartmentShortResponse.builder()
                        .id(s.getDepartment().getId())
                        .name(s.getDepartment().getName())
                        .build())
                .build();
    }
}
