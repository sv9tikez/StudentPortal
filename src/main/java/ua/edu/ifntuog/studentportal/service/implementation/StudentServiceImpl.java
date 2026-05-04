package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.entity.Group;
import ua.edu.ifntuog.studentportal.entity.Student;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.GroupRepo;
import ua.edu.ifntuog.studentportal.repository.StudentRepo;
import ua.edu.ifntuog.studentportal.service.StudentService;
import ua.edu.ifntuog.studentportal.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserService userService;
    private final StudentRepo studentRepo;
    private final GroupRepo groupRepo;

    @Override
    @Transactional
    public Student create(User user) {
        if (studentRepo.existsById(user.getId())) {
            throw new EntityAlreadyExistsException("Student with id " + user.getId() + " already exists");
        }

        Student student = new Student();
        userService.addRole(user.getId(), RoleType.ROLE_STUDENT);
        student.setUser(user);
        return studentRepo.save(student);
    }

    @Override
    public Student getById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found: " + id));
    }

    @Override
    public List<Student> getAll() {
        return studentRepo.findAll();
    }

    @Override
    public List<Student> getAllByGroupId(Long groupId) {
        return studentRepo.findAllByGroupId(groupId);
    }

    @Override
    @Transactional
    public Student update(Long id, Student updated) {
        Student student = getById(id);
        student.setGroup(updated.getGroup());
        return student;
    }

    @Override
    public void delete(Long id) {
        Student student = getById(id);
        studentRepo.delete(student);
    }

    @Override
    @Transactional
    public Student assignToGroup(Long studentId, Long groupId) {
        Student student = getById(studentId);
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found: " + groupId));
        student.setGroup(group);
        return student;
    }
}
