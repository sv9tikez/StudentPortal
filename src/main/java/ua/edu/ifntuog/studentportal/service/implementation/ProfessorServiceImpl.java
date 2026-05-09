package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.response.ProfessorResponse;
import ua.edu.ifntuog.studentportal.dto.request.UpdateProfessorRequest;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.entity.Professor;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.ProfessorRepo;
import ua.edu.ifntuog.studentportal.repository.UserRepo;
import ua.edu.ifntuog.studentportal.service.ProfessorService;
import ua.edu.ifntuog.studentportal.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final UserService userService;
    private final UserRepo userRepo;
    private final ProfessorRepo professorRepo;
    private final DepartmentRepo departmentRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ProfessorResponse save(Long userId, Long departmentId, String academicTitle) {
        if (professorRepo.existsById(userId)) {
            throw new EntityAlreadyExistsException("Professor with id " + userId + " already exists");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        Professor professor = new Professor();
        userService.addRole(user.getId(), RoleType.ROLE_PROFESSOR);
        professor.setUser(user);
        professor.setDepartment(findDepartmentById(departmentId));
        professor.setAcademicTitle(academicTitle);
        return modelMapper.map(professorRepo.save(professor), ProfessorResponse.class);
    }

    @Override
    public ProfessorResponse findById(Long id) {
        return modelMapper.map(findProfessorById(id), ProfessorResponse.class);
    }

    @Override
    public List<ProfessorResponse> findAll() {
        return modelMapper.map(professorRepo.findAll(), new TypeToken<List<ProfessorResponse>>() {
        }.getType());
    }

    @Override
    public List<ProfessorResponse> findAllByDepartmentId(Long departmentId) {
        return modelMapper.map(professorRepo.findAllByDepartmentId(departmentId), new TypeToken<List<ProfessorResponse>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void update(Long id, UpdateProfessorRequest updated) {
        Professor professor = findProfessorById(id);
        userService.update(id, updated);

        if (updated.getAcademicTitle() != null) {
            professor.setAcademicTitle(updated.getAcademicTitle());
        }
        if (updated.getDepartmentId() != null) {
            professor.setAcademicTitle(updated.getAcademicTitle());
        }
        professorRepo.save(professor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Professor professor = findProfessorById(id);
        professorRepo.delete(professor);
    }

    @Override
    @Transactional
    public ProfessorResponse assignToDepartment(Long professorId, Long departmentId) {
        Professor professor = findProfessorById(professorId);
        professor.setDepartment(findDepartmentById(departmentId));
        return modelMapper.map(professorRepo.save(professor), ProfessorResponse.class);
    }

    private Professor findProfessorById(Long id) {
        return professorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
    }

    private Department findDepartmentById(Long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
    }
}
