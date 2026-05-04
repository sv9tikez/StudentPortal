package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.entity.Professor;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.ProfessorRepo;
import ua.edu.ifntuog.studentportal.service.ProfessorService;
import ua.edu.ifntuog.studentportal.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final UserService userService;
    private final ProfessorRepo professorRepo;
    private final DepartmentRepo departmentRepo;

    @Override
    @Transactional
    public Professor create(User user) {
        if (professorRepo.existsById(user.getId())) {
            throw new EntityAlreadyExistsException("Professor with id " + user.getId() + " already exists");
        }

        Professor professor = new Professor();
        userService.addRole(user.getId(), RoleType.ROLE_PROFESSOR);
        professor.setUser(user);
        return professorRepo.save(professor);
    }

    @Override
    public Professor getById(Long id) {
        return professorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor not found: " + id));
    }

    @Override
    public List<Professor> getAll() {
        return professorRepo.findAll();
    }

    @Override
    public List<Professor> getAllByDepartmentId(Long departmentId) {
        return professorRepo.findAllByDepartmentId(departmentId);
    }

    @Override
    @Transactional
    public Professor update(Long id, Professor updated) {
        Professor professor = getById(id);
        professor.setAcademicTitle(updated.getAcademicTitle());
        professor.setDepartment(updated.getDepartment());
        return professorRepo.save(professor);
    }

    @Override
    public void delete(Long id) {
        Professor professor = getById(id);
        professorRepo.delete(professor);
    }

    @Override
    @Transactional
    public Professor assignToDepartment(Long professorId, Long departmentId) {
        Professor professor = getById(professorId);
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + departmentId));
        professor.setDepartment(department);
        return professorRepo.save(professor);
    }
}
