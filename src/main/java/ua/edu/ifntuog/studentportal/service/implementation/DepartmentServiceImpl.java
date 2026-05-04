package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.service.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepo departmentRepo;

    @Override
    public Department create(Department department) {
        return departmentRepo.save(department);
    }

    @Override
    public Department getById(Long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
    }

    @Override
    public List<Department> getAll() {
        return departmentRepo.findAll();
    }

    @Override
    public List<Department> getAllByFacultyId(Long facultyId) {
        return departmentRepo.findAllByFacultyId(facultyId);
    }

    @Override
    @Transactional
    public Department update(Long id, Department updated) {
        Department department = getById(id);
        department.setName(updated.getName());
        department.setFaculty(updated.getFaculty());
        return departmentRepo.save(department);
    }

    @Override
    public void delete(Long id) {
        Department department = getById(id);
        departmentRepo.delete(department);
    }
}
