package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ifntuog.studentportal.dto.request.CourseRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateCourseRequest;
import ua.edu.ifntuog.studentportal.dto.response.CourseResponse;
import ua.edu.ifntuog.studentportal.entity.Course;
import ua.edu.ifntuog.studentportal.entity.Group;
import ua.edu.ifntuog.studentportal.entity.Professor;
import ua.edu.ifntuog.studentportal.entity.Subject;
import ua.edu.ifntuog.studentportal.exception.DuplicateCourseException;
import ua.edu.ifntuog.studentportal.repository.CourseRepo;
import ua.edu.ifntuog.studentportal.repository.GroupRepo;
import ua.edu.ifntuog.studentportal.repository.ProfessorRepo;
import ua.edu.ifntuog.studentportal.repository.SubjectRepo;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    private static final Long COURSE_ID = 1L;
    private static final Long SUBJECT_ID = 10L;
    private static final Long PROFESSOR_ID = 20L;
    private static final Long GROUP_ID = 30L;
    private static final Integer YEAR = 2026;

    private CourseRequest request;
    private UpdateCourseRequest updateRequest;
    private Course course;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private SubjectRepo subjectRepo;

    @Mock
    private ProfessorRepo professorRepo;

    @Mock
    private GroupRepo groupRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        request = new CourseRequest();
        request.setYear(YEAR);
        request.setSubjectId(SUBJECT_ID);
        request.setProfessorId(PROFESSOR_ID);
        request.setGroupId(GROUP_ID);

        updateRequest = new UpdateCourseRequest(2025, SUBJECT_ID, PROFESSOR_ID, GROUP_ID);

        course = new Course();
        course.setId(COURSE_ID);
    }

    @Test
    void create_success() {
        when(courseRepo.existsByYearAndSubject_IdAndProfessor_IdAndGroup_Id(YEAR, SUBJECT_ID, PROFESSOR_ID, GROUP_ID))
                .thenReturn(false);
        when(subjectRepo.findById(SUBJECT_ID)).thenReturn(Optional.of(new Subject()));
        when(professorRepo.findById(PROFESSOR_ID)).thenReturn(Optional.of(new Professor()));
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.of(new Group()));

        when(courseRepo.save(any(Course.class))).thenReturn(course);
        when(modelMapper.map(course, CourseResponse.class)).thenReturn(new CourseResponse());

        CourseResponse response = courseService.create(request);

        assertNotNull(response);
        verify(courseRepo).save(any(Course.class));
    }

    @Test
    void create_failure_whenDuplicate() {
        when(courseRepo.existsByYearAndSubject_IdAndProfessor_IdAndGroup_Id(YEAR, SUBJECT_ID, PROFESSOR_ID, GROUP_ID))
                .thenReturn(true);

        assertThrows(DuplicateCourseException.class, () -> courseService.create(request));
    }

    @Test
    void findById_success() {
        when(courseRepo.findById(COURSE_ID)).thenReturn(Optional.of(course));
        when(modelMapper.map(course, CourseResponse.class)).thenReturn(new CourseResponse());

        assertNotNull(courseService.findById(COURSE_ID));
    }

    @Test
    void findById_failure_whenMissing() {
        when(courseRepo.findById(COURSE_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.findById(COURSE_ID));
    }

    @Test
    void findAll_success() {
        when(courseRepo.findAll()).thenReturn(List.of(new Course()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new CourseResponse()));

        List<CourseResponse> results = courseService.findAll();

        assertEquals(1, results.size());
    }

    @Test
    void findAll_failure_whenRepoFails() {
        when(courseRepo.findAll()).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> courseService.findAll());
    }

    @Test
    void findAllByGroupId_success() {
        when(courseRepo.findAllByGroupId(GROUP_ID)).thenReturn(List.of(new Course()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new CourseResponse(), new CourseResponse()));

        List<CourseResponse> results = courseService.findAllByGroupId(GROUP_ID);

        assertEquals(2, results.size());
    }

    @Test
    void findAllByGroupId_failure_whenRepoFails() {
        when(courseRepo.findAllByGroupId(GROUP_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> courseService.findAllByGroupId(GROUP_ID));
    }

    @Test
    void findAllByProfessorId_success() {
        when(courseRepo.findAllByProfessorId(PROFESSOR_ID)).thenReturn(List.of(new Course()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new CourseResponse()));

        List<CourseResponse> results = courseService.findAllByProfessorId(PROFESSOR_ID);

        assertEquals(1, results.size());
    }

    @Test
    void findAllByProfessorId_failure_whenRepoFails() {
        when(courseRepo.findAllByProfessorId(PROFESSOR_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> courseService.findAllByProfessorId(PROFESSOR_ID));
    }

    @Test
    void findAllBySubjectId_success() {
        when(courseRepo.findAllBySubjectId(SUBJECT_ID)).thenReturn(List.of(new Course()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new CourseResponse()));

        List<CourseResponse> results = courseService.findAllBySubjectId(SUBJECT_ID);

        assertEquals(1, results.size());
    }

    @Test
    void findAllBySubjectId_failure_whenRepoFails() {
        when(courseRepo.findAllBySubjectId(SUBJECT_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> courseService.findAllBySubjectId(SUBJECT_ID));
    }

    @Test
    void update_success() {
        when(courseRepo.findById(COURSE_ID))
                .thenReturn(Optional.of(course));
        when(subjectRepo.findById(SUBJECT_ID)).thenReturn(Optional.of(new Subject()));
        when(professorRepo.findById(PROFESSOR_ID)).thenReturn(Optional.of(new Professor()));
        when(groupRepo.findById(GROUP_ID)).thenReturn(Optional.of(new Group()));

        courseService.update(COURSE_ID, updateRequest);

        verify(courseRepo).save(course);
        assertEquals(2025, course.getYear());
    }

    @Test
    void update_failure_whenMissing() {
        when(courseRepo.findById(COURSE_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.update(COURSE_ID, updateRequest));
    }

    @Test
    void delete_success() {
        when(courseRepo.findById(COURSE_ID)).thenReturn(Optional.of(course));

        courseService.delete(COURSE_ID);

        verify(courseRepo).delete(course);
    }

    @Test
    void delete_failure_whenMissing() {
        when(courseRepo.findById(COURSE_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.delete(COURSE_ID));
    }
}
