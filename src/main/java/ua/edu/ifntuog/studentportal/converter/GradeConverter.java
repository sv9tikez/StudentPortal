package ua.edu.ifntuog.studentportal.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import ua.edu.ifntuog.studentportal.dto.response.CourseShortResponse;
import ua.edu.ifntuog.studentportal.dto.response.GradeResponse;
import ua.edu.ifntuog.studentportal.dto.response.GroupShortResponse;
import ua.edu.ifntuog.studentportal.dto.response.StudentShortResponse;
import ua.edu.ifntuog.studentportal.entity.Grade;

@Component
public class GradeConverter implements Converter<Grade, GradeResponse> {
    @Override
    public GradeResponse convert(MappingContext<Grade, GradeResponse> context) {
        Grade s = context.getSource();
        return GradeResponse.builder()
                .id(s.getId())
                .grade(s.getGrade())
                .type(s.getType())
                .date(s.getDate())
                .student(StudentShortResponse.builder()
                        .id(s.getStudent().getId())
                        .firstName(s.getStudent().getUser().getFirstName())
                        .lastName(s.getStudent().getUser().getLastName())
                        .group(GroupShortResponse.builder()
                                .id(s.getStudent().getGroup().getId())
                                .name(s.getStudent().getGroup().getName())
                                .build())
                        .build())
                .course(CourseShortResponse.builder()
                        .id(s.getCourse().getId())
                        .name(s.getCourse().getSubject().getName())
                        .build())
                .build();
    }
}
