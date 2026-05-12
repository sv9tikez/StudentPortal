package ua.edu.ifntuog.studentportal.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import ua.edu.ifntuog.studentportal.dto.response.CourseResponse;
import ua.edu.ifntuog.studentportal.dto.response.GroupShortResponse;
import ua.edu.ifntuog.studentportal.dto.response.ProfessorShortResponse;
import ua.edu.ifntuog.studentportal.dto.response.SubjectShortResponse;
import ua.edu.ifntuog.studentportal.entity.Course;

@Component
public class CourseConverter implements Converter<Course, CourseResponse> {
    @Override
    public CourseResponse convert(MappingContext<Course, CourseResponse> context) {
        Course s = context.getSource();
        return CourseResponse.builder()
                .id(s.getId())
                .year(s.getYear())
                .subject(SubjectShortResponse.builder()
                        .id(s.getSubject().getId())
                        .name(s.getSubject().getName())
                        .build())
                .professor(ProfessorShortResponse.builder()
                        .id(s.getProfessor().getId())
                        .firstName(s.getProfessor().getUser().getFirstName())
                        .lastName(s.getProfessor().getUser().getLastName())
                        .build())
                .group(GroupShortResponse.builder()
                        .id(s.getGroup().getId())
                        .name(s.getGroup().getName())
                        .build())
                .build();
    }
}
