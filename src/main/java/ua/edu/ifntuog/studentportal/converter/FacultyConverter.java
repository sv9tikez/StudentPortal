package ua.edu.ifntuog.studentportal.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import ua.edu.ifntuog.studentportal.dto.response.FacultyResponse;
import ua.edu.ifntuog.studentportal.entity.Faculty;

@Component
public class FacultyConverter implements Converter<Faculty, FacultyResponse> {
    @Override
    public FacultyResponse convert(MappingContext<Faculty, FacultyResponse> context) {
        Faculty s = context.getSource();
        return FacultyResponse.builder()
                .id(s.getId())
                .name(s.getName())
                .build();
    }
}
