package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.request.FacultyRequest;
import ua.edu.ifntuog.studentportal.dto.response.FacultyResponse;
import ua.edu.ifntuog.studentportal.entity.Faculty;
import ua.edu.ifntuog.studentportal.repository.FacultyRepo;
import ua.edu.ifntuog.studentportal.service.FacultyService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepo facultyRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public FacultyResponse create(FacultyRequest dto) {

        if (facultyRepo.existsByName(dto.getName())) {
            throw new EntityExistsException("Faculty with name " + dto.getName() + " already exists");
        }

        Faculty faculty = new Faculty();
        faculty.setName(dto.getName());

        return modelMapper.map(facultyRepo.save(faculty), FacultyResponse.class);
    }

    @Override
    public FacultyResponse findById(Long id) {
        return modelMapper.map(getFacultyById(id), FacultyResponse.class);
    }

    @Override
    public FacultyResponse findByName(String name) {
        return modelMapper.map(getFacultyByName(name), FacultyResponse.class);
    }

    @Override
    public List<FacultyResponse> findAll() {
        return modelMapper.map(facultyRepo.findAll(), new TypeToken<List<FacultyResponse>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void update(Long id, FacultyRequest updated) {
        Faculty faculty = getFacultyById(id);
        faculty.setName(updated.getName());
        facultyRepo.save(faculty);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Faculty faculty = getFacultyById(id);
        facultyRepo.delete(faculty);
    }

    private Faculty getFacultyById(Long id) {
        return facultyRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found: " + id));
    }

    private Faculty getFacultyByName(String name) {
        return facultyRepo.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found: " + name));
    }
}
