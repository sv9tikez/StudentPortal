package ua.edu.ifntuog.studentportal.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import ua.edu.ifntuog.studentportal.dto.response.DepartmentResponse;
import ua.edu.ifntuog.studentportal.dto.response.FacultyResponse;
import ua.edu.ifntuog.studentportal.entity.Department;

@Component
public class DepartmentConverter implements Converter<Department, DepartmentResponse> {
    @Override
    public DepartmentResponse convert(MappingContext<Department, DepartmentResponse> context) {
        Department s = context.getSource();
        return DepartmentResponse.builder()
                .id(s.getId())
                .name(s.getName())
                .faculty(FacultyResponse.builder()
                        .id(s.getFaculty().getId())
                        .name(s.getFaculty().getName())
                        .build())
                .build();
    }
}
