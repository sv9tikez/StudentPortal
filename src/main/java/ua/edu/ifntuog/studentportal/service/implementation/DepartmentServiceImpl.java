package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.DepartmentRequest;
import ua.edu.ifntuog.studentportal.dto.DepartmentResponse;
import ua.edu.ifntuog.studentportal.dto.UpdateDepartmentRequest;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.entity.Faculty;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.FacultyRepo;
import ua.edu.ifntuog.studentportal.service.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final FacultyRepo facultyRepo;
    private final ModelMapper modelMapper;

    @Override
    public DepartmentResponse create(DepartmentRequest dto) {

        if (departmentRepo.existsByName(dto.getName())) {
            throw new EntityAlreadyExistsException("Department with name " + dto.getName() + " already exists");
        }

        Department department = new Department();
        department.setName(dto.getName());
        department.setFaculty(findFacultyById(dto.getFacultyId()));
        return modelMapper.map(departmentRepo.save(department), DepartmentResponse.class);
    }

    @Override
    public DepartmentResponse findById(Long id) {
        return modelMapper.map(findDepartmentById(id), DepartmentResponse.class);
    }

    @Override
    public DepartmentResponse findByName(String name) {
        return modelMapper.map(findDepartmentByName(name), DepartmentResponse.class);
    }

    @Override
    public List<DepartmentResponse> findAll() {
        return modelMapper.map(departmentRepo.findAll(), new TypeToken<List<DepartmentResponse>>() {
        }.getType());
    }

    @Override
    public List<DepartmentResponse> findAllByFacultyId(Long facultyId) {
        return modelMapper.map(departmentRepo.findAllByFacultyId(facultyId), new TypeToken<List<DepartmentResponse>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void update(Long id, UpdateDepartmentRequest updated) {
        Department department = findDepartmentById(id);

        department.setName(updated.getName());
        if (updated.getFacultyId() != null) {
            department.setFaculty(findFacultyById(updated.getFacultyId()));
        }
        departmentRepo.save(department);
    }

    @Override
    public void delete(Long id) {
        Department department = findDepartmentById(id);
        departmentRepo.delete(department);
    }

    @Override
    @Transactional
    public DepartmentResponse assignToFaculty(Long departmentId, Long facultyId) {
        Department department = findDepartmentById(departmentId);
        Faculty faculty = findFacultyById(facultyId);
        department.setFaculty(faculty);
        return modelMapper.map(departmentRepo.save(department), DepartmentResponse.class);
    }

    private Department findDepartmentById(Long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
    }

    private Department findDepartmentByName(String name) {
        return departmentRepo.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + name));
    }

    private Faculty findFacultyById(Long facultyId) {
        return facultyRepo.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found: " + facultyId));
    }
}
