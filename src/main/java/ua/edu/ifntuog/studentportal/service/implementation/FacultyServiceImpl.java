package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.entity.Faculty;
import ua.edu.ifntuog.studentportal.repository.FacultyRepo;
import ua.edu.ifntuog.studentportal.service.FacultyService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepo facultyRepo;

    @Override
    public Faculty create(Faculty faculty) {
        return facultyRepo.save(faculty);
    }

    @Override
    public Faculty getById(Long id) {
        return facultyRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found: " + id));
    }

    @Override
    public Faculty getByName(String name) {
        return facultyRepo.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found: " + name));
    }

    @Override
    public List<Faculty> getAll() {
        return facultyRepo.findAll();
    }

    @Override
    @Transactional
    public Faculty update(Long id, Faculty updated) {
        Faculty faculty = getById(id);
        faculty.setName(updated.getName());
        return facultyRepo.save(faculty);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Faculty faculty = getById(id);
        facultyRepo.delete(faculty);
    }
}
