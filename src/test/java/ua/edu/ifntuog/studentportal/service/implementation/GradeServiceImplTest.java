package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ifntuog.studentportal.dto.request.GradeRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateGradeRequest;
import ua.edu.ifntuog.studentportal.dto.response.GradeResponse;
import ua.edu.ifntuog.studentportal.entity.Course;
import ua.edu.ifntuog.studentportal.entity.Grade;
import ua.edu.ifntuog.studentportal.entity.Student;
import ua.edu.ifntuog.studentportal.enums.GradeType;
import ua.edu.ifntuog.studentportal.repository.CourseRepo;
import ua.edu.ifntuog.studentportal.repository.GradeRepo;
import ua.edu.ifntuog.studentportal.repository.StudentRepo;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeServiceImplTest {

    private static final Long STUDENT_ID = 1L;
    private static final Long COURSE_ID = 2L;
    private static final Long GRADE_ID = 5L;

    private GradeRequest request;
    private UpdateGradeRequest updateRequest;
    private Student student;
    private Course course;
    private Grade grade;

    @Mock
    private GradeRepo gradeRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GradeServiceImpl gradeService;

    @BeforeEach
    void setUp() {
        request = new GradeRequest();
        request.setGrade(90);
        request.setType(GradeType.EXAM);
        request.setDate(LocalDate.now());
        request.setStudentId(STUDENT_ID);
        request.setCourseId(COURSE_ID);

        updateRequest = new UpdateGradeRequest(95, GradeType.TEST, LocalDate.now(), STUDENT_ID, COURSE_ID);

        student = new Student();
        student.setId(STUDENT_ID);

        course = new Course();
        course.setId(COURSE_ID);

        grade = new Grade();
        grade.setId(GRADE_ID);
    }

    @Test
    void create_success() {
        when(studentRepo.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(courseRepo.findById(COURSE_ID)).thenReturn(Optional.of(course));
        when(gradeRepo.save(any(Grade.class))).thenReturn(grade);
        when(modelMapper.map(grade, GradeResponse.class)).thenReturn(new GradeResponse());

        GradeResponse response = gradeService.create(request);

        assertNotNull(response);
        verify(gradeRepo).save(any(Grade.class));
    }

    @Test
    void create_failure_whenStudentMissing() {
        when(studentRepo.findById(STUDENT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gradeService.create(request));
    }

    @Test
    void findById_success() {
        when(gradeRepo.findById(GRADE_ID)).thenReturn(Optional.of(grade));
        when(modelMapper.map(grade, GradeResponse.class)).thenReturn(new GradeResponse());

        assertNotNull(gradeService.findById(GRADE_ID));
    }

    @Test
    void findById_failure_whenMissing() {
        when(gradeRepo.findById(GRADE_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gradeService.findById(GRADE_ID));
    }

    @Test
    void findAllByStudentId_success() {
        when(gradeRepo.findAllByStudentId(STUDENT_ID)).thenReturn(List.of(new Grade()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new GradeResponse()));

        List<GradeResponse> results = gradeService.findAllByStudentId(STUDENT_ID);

        assertEquals(1, results.size());
    }

    @Test
    void findAllByStudentId_failure_whenRepoFails() {
        when(gradeRepo.findAllByStudentId(STUDENT_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> gradeService.findAllByStudentId(STUDENT_ID));
    }

    @Test
    void findAllByCourseId_success() {
        when(gradeRepo.findAllByCourseId(COURSE_ID)).thenReturn(List.of(new Grade(), new Grade()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new GradeResponse()));

        List<GradeResponse> results = gradeService.findAllByCourseId(COURSE_ID);

        assertEquals(1, results.size());
    }

    @Test
    void findAllByCourseId_failure_whenRepoFails() {
        when(gradeRepo.findAllByCourseId(COURSE_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> gradeService.findAllByCourseId(COURSE_ID));
    }

    @Test
    void findAllByStudentIdAndCourseId_success() {
        when(gradeRepo.findAllByStudentIdAndCourseId(STUDENT_ID, COURSE_ID)).thenReturn(List.of(new Grade()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new GradeResponse(), new GradeResponse()));

        List<GradeResponse> results = gradeService.findAllByStudentIdAndCourseId(STUDENT_ID, COURSE_ID);

        assertEquals(2, results.size());
    }

    @Test
    void findAllByStudentIdAndCourseId_failure_whenRepoFails() {
        when(gradeRepo.findAllByStudentIdAndCourseId(STUDENT_ID, COURSE_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> gradeService.findAllByStudentIdAndCourseId(STUDENT_ID, COURSE_ID));
    }

    @Test
    void update_success() {
        when(gradeRepo.findById(GRADE_ID)).thenReturn(Optional.of(grade));
        when(studentRepo.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(courseRepo.findById(COURSE_ID)).thenReturn(Optional.of(course));

        gradeService.update(GRADE_ID, updateRequest);

        verify(gradeRepo).save(grade);
        assertEquals(95, grade.getGrade());
    }

    @Test
    void update_failure_whenMissing() {
        when(gradeRepo.findById(GRADE_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gradeService.update(GRADE_ID, updateRequest));
    }

    @Test
    void delete_success() {
        when(gradeRepo.findById(GRADE_ID)).thenReturn(Optional.of(grade));

        gradeService.delete(GRADE_ID);

        verify(gradeRepo).delete(grade);
    }

    @Test
    void delete_failure_whenMissing() {
        when(gradeRepo.findById(GRADE_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gradeService.delete(GRADE_ID));
    }

    @Test
    void findAverageGradeByStudentId_success() {
        Grade grade1 = new Grade();
        grade1.setGrade(80);
        Grade grade2 = new Grade();
        grade2.setGrade(90);

        when(gradeRepo.findAllByStudentId(STUDENT_ID)).thenReturn(List.of(grade1, grade2));

        Double average = gradeService.findAverageGradeByStudentId(STUDENT_ID);

        assertEquals(85.0, average);
    }

    @Test
    void findAverageGradeByStudentId_failure_whenRepoFails() {
        when(gradeRepo.findAllByStudentId(STUDENT_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> gradeService.findAverageGradeByStudentId(STUDENT_ID));
    }
}
