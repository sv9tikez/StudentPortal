package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.GradeRequest;
import ua.edu.ifntuog.studentportal.dto.GradeResponse;
import ua.edu.ifntuog.studentportal.dto.UpdateGradeRequest;
import ua.edu.ifntuog.studentportal.entity.Course;
import ua.edu.ifntuog.studentportal.entity.Grade;
import ua.edu.ifntuog.studentportal.entity.Student;
import ua.edu.ifntuog.studentportal.repository.CourseRepo;
import ua.edu.ifntuog.studentportal.repository.GradeRepo;
import ua.edu.ifntuog.studentportal.repository.StudentRepo;
import ua.edu.ifntuog.studentportal.service.GradeService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepo gradeRepo;
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public GradeResponse create(GradeRequest dto) {

        Grade grade = new Grade();

        grade.setGrade(dto.getGrade());
        grade.setType(dto.getType());
        grade.setDate(dto.getDate());
        grade.setStudent(findStudentById(dto.getStudentId()));
        grade.setCourse(findCourseById(dto.getCourseId()));
        grade.setCreatedAt(LocalDateTime.now());

        return modelMapper.map(gradeRepo.save(grade), GradeResponse.class);
    }

    @Override
    public GradeResponse findById(Long id) {
        return modelMapper.map(getGradeById(id), GradeResponse.class);
    }

    @Override
    public List<GradeResponse> findAllByStudentId(Long studentId) {
        return modelMapper.map(gradeRepo.findAllByStudentId(studentId), new TypeToken<List<GradeResponse>>() {
        }.getType());
    }

    @Override
    public List<GradeResponse> findAllByCourseId(Long courseId) {
        return modelMapper.map(gradeRepo.findAllByCourseId(courseId), new TypeToken<List<GradeResponse>>() {
        }.getType());
    }

    @Override
    public List<GradeResponse> findAllByStudentIdAndCourseId(Long studentId, Long courseId) {
        return modelMapper.map(gradeRepo.findAllByStudentIdAndCourseId(studentId, courseId), new TypeToken<List<GradeResponse>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void update(Long id, UpdateGradeRequest updated) {
        Grade grade = getGradeById(id);

        if (updated.getGrade() != null) {
            grade.setGrade(updated.getGrade());
        }

        if (updated.getType() != null) {
            grade.setType(updated.getType());
        }

        if (updated.getDate() != null) {
            grade.setDate(updated.getDate());
        }

        if (updated.getStudentId() != null) {
            grade.setStudent(findStudentById(updated.getStudentId()));
        }

        if (updated.getCourseId() != null) {
            grade.setCourse(findCourseById(updated.getCourseId()));
        }

        gradeRepo.save(grade);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Grade grade = getGradeById(id);
        gradeRepo.delete(grade);
    }

    @Override
    public Double findAverageGradeByStudentId(Long studentId) {
        return gradeRepo.findAllByStudentId(studentId).stream()
                .mapToInt(Grade::getGrade)
                .average()
                .orElse(0.0);
    }

    private Grade getGradeById(Long id) {
        return gradeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found: " + id));
    }

    private Student findStudentById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found id: " + id));
    }

    private Course findCourseById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + id));
    }
}
