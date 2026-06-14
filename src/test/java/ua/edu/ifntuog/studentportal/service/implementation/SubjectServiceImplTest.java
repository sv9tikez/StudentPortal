package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ifntuog.studentportal.dto.request.SubjectRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateSubjectRequest;
import ua.edu.ifntuog.studentportal.dto.response.SubjectResponse;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.entity.Subject;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.SubjectRepo;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    private static final Long SUBJECT_ID = 1L;
    private static final Long DEPARTMENT_ID = 10L;
    private static final String SUBJECT_NAME = "Math";

    private SubjectRequest request;
    private UpdateSubjectRequest updateRequest;
    private Department department;
    private Subject subject;

    @Mock
    private SubjectRepo subjectRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @BeforeEach
    void setUp() {
        request = new SubjectRequest();
        request.setName(SUBJECT_NAME);
        request.setCredits(3);
        request.setHours(30);
        request.setDepartmentId(DEPARTMENT_ID);

        updateRequest = new UpdateSubjectRequest("Physics", 4, 40, 5L);

        department = new Department();
        department.setId(DEPARTMENT_ID);

        subject = new Subject();
        subject.setId(SUBJECT_ID);
    }

    @Test
    void create_success() {
        Subject saved = new Subject();
        saved.setId(SUBJECT_ID);

        when(subjectRepo.existsByName(SUBJECT_NAME)).thenReturn(false);
        when(departmentRepo.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));
        when(subjectRepo.save(any(Subject.class))).thenReturn(saved);
        when(modelMapper.map(saved, SubjectResponse.class)).thenReturn(new SubjectResponse());

        SubjectResponse response = subjectService.create(request);

        assertNotNull(response);
        verify(subjectRepo).save(any(Subject.class));
    }

    @Test
    void create_failure_whenDuplicate() {
        when(subjectRepo.existsByName(SUBJECT_NAME)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> subjectService.create(request));
        verify(subjectRepo, never()).save(any(Subject.class));
    }

    @Test
    void findById_success() {
        when(subjectRepo.findById(SUBJECT_ID)).thenReturn(Optional.of(subject));
        when(modelMapper.map(subject, SubjectResponse.class)).thenReturn(new SubjectResponse());

        assertNotNull(subjectService.findById(SUBJECT_ID));
    }

    @Test
    void findById_failure_whenMissing() {
        when(subjectRepo.findById(SUBJECT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> subjectService.findById(SUBJECT_ID));
    }

    @Test
    void findAll_success() {
        when(subjectRepo.findAll()).thenReturn(List.of(new Subject(), new Subject()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new SubjectResponse()));

        List<SubjectResponse> results = subjectService.findAll();

        assertEquals(1, results.size());
    }

    @Test
    void findAll_failure_whenRepoFails() {
        when(subjectRepo.findAll()).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> subjectService.findAll());
    }

    @Test
    void findAllByDepartmentId_success() {
        when(subjectRepo.findAllByDepartmentId(DEPARTMENT_ID)).thenReturn(List.of(new Subject()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new SubjectResponse(), new SubjectResponse()));

        List<SubjectResponse> results = subjectService.findAllByDepartmentId(DEPARTMENT_ID);

        assertEquals(2, results.size());
    }

    @Test
    void findAllByDepartmentId_failure_whenRepoFails() {
        when(subjectRepo.findAllByDepartmentId(DEPARTMENT_ID)).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> subjectService.findAllByDepartmentId(DEPARTMENT_ID));
    }

    @Test
    void update_success() {
        when(subjectRepo.findById(SUBJECT_ID)).thenReturn(Optional.of(subject));
        when(departmentRepo.findById(5L)).thenReturn(Optional.of(new Department()));

        subjectService.update(SUBJECT_ID, updateRequest);

        verify(subjectRepo).save(subject);
        assertEquals("Physics", subject.getName());
    }

    @Test
    void update_failure_whenMissing() {
        when(subjectRepo.findById(SUBJECT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> subjectService.update(SUBJECT_ID, updateRequest));
    }

    @Test
    void delete_success() {
        when(subjectRepo.findById(SUBJECT_ID)).thenReturn(Optional.of(subject));

        subjectService.delete(SUBJECT_ID);

        verify(subjectRepo).delete(subject);
    }

    @Test
    void delete_failure_whenMissing() {
        when(subjectRepo.findById(SUBJECT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> subjectService.delete(SUBJECT_ID));
    }
}
