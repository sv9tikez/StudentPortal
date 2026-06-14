package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.ifntuog.studentportal.dto.request.FacultyRequest;
import ua.edu.ifntuog.studentportal.dto.response.FacultyResponse;
import ua.edu.ifntuog.studentportal.entity.Faculty;
import ua.edu.ifntuog.studentportal.repository.FacultyRepo;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    private static final Long FACULTY_ID = 1L;
    private static final String FACULTY_NAME = "Факультет інформаційних технологій";

    private FacultyRequest request;
    private Faculty faculty;

    @Mock
    private FacultyRepo facultyRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @BeforeEach
    void setUp() {
        request = new FacultyRequest();
        request.setName(FACULTY_NAME);

        faculty = new Faculty();
        faculty.setId(FACULTY_ID);
    }

    @Test
    void create_success() {
        when(facultyRepo.existsByName(FACULTY_NAME)).thenReturn(false);
        when(facultyRepo.save(any(Faculty.class))).thenReturn(faculty);
        when(modelMapper.map(faculty, FacultyResponse.class)).thenReturn(new FacultyResponse());

        FacultyResponse response = facultyService.create(request);

        assertNotNull(response);
    }

    @Test
    void create_failure_whenDuplicate() {
        when(facultyRepo.existsByName(FACULTY_NAME)).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> facultyService.create(request));
    }

    @Test
    void findById_success() {
        when(facultyRepo.findById(FACULTY_ID)).thenReturn(Optional.of(faculty));
        when(modelMapper.map(faculty, FacultyResponse.class)).thenReturn(new FacultyResponse());

        assertNotNull(facultyService.findById(FACULTY_ID));
    }

    @Test
    void findById_failure_whenMissing() {
        when(facultyRepo.findById(FACULTY_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> facultyService.findById(FACULTY_ID));
    }

    @Test
    void findByName_success() {
        when(facultyRepo.findByName(FACULTY_NAME)).thenReturn(Optional.of(faculty));
        when(modelMapper.map(faculty, FacultyResponse.class)).thenReturn(new FacultyResponse());

        assertNotNull(facultyService.findByName(FACULTY_NAME));
    }

    @Test
    void findByName_failure_whenMissing() {
        when(facultyRepo.findByName(FACULTY_NAME)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> facultyService.findByName(FACULTY_NAME));
    }

    @Test
    void findAll_success() {
        when(facultyRepo.findAll()).thenReturn(List.of(new Faculty()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new FacultyResponse(), new FacultyResponse()));

        List<FacultyResponse> results = facultyService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void findAll_failure_whenRepoFails() {
        when(facultyRepo.findAll()).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> facultyService.findAll());
    }

    @Test
    void update_success() {
        FacultyRequest update = new FacultyRequest();
        update.setName("Інститут інформаційних технологій");

        when(facultyRepo.findById(FACULTY_ID)).thenReturn(Optional.of(faculty));

        facultyService.update(FACULTY_ID, update);

        verify(facultyRepo).save(faculty);
        assertEquals("Інститут інформаційних технологій", faculty.getName());
    }

    @Test
    void update_failure_whenMissing() {
        FacultyRequest update = new FacultyRequest();
        update.setName("Інститут інформаційних технологій");

        when(facultyRepo.findById(FACULTY_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> facultyService.update(FACULTY_ID, update));
    }

    @Test
    void delete_success() {
        when(facultyRepo.findById(FACULTY_ID)).thenReturn(Optional.of(faculty));

        facultyService.delete(FACULTY_ID);

        verify(facultyRepo).delete(faculty);
    }

    @Test
    void delete_failure_whenMissing() {
        when(facultyRepo.findById(FACULTY_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> facultyService.delete(FACULTY_ID));
    }
}
