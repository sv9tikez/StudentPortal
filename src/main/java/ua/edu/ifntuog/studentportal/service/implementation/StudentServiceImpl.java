package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.response.StudentResponse;
import ua.edu.ifntuog.studentportal.dto.request.UpdateStudentRequest;
import ua.edu.ifntuog.studentportal.entity.Group;
import ua.edu.ifntuog.studentportal.entity.Student;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.GroupRepo;
import ua.edu.ifntuog.studentportal.repository.StudentRepo;
import ua.edu.ifntuog.studentportal.repository.UserRepo;
import ua.edu.ifntuog.studentportal.service.StudentService;
import ua.edu.ifntuog.studentportal.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserService userService;
    private final UserRepo userRepo;
    private final StudentRepo studentRepo;
    private final GroupRepo groupRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public StudentResponse save(Long userId, Long groupId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        if (studentRepo.existsById(userId)) {
            throw new EntityAlreadyExistsException("Student with id " + userId + " already exists");
        }

        userService.addRole(user.getId(), RoleType.ROLE_STUDENT);
        Student student = new Student();
        student.setUser(user);
        student.setGroup(findGroupById(groupId));
        return modelMapper.map(studentRepo.save(student), StudentResponse.class);
    }

    @Override
    public StudentResponse findById(Long id) {
        return modelMapper.map(findStudentById(id), StudentResponse.class);
    }

    @Override
    public List<StudentResponse> findAll() {
        return modelMapper.map(studentRepo.findAll(), new TypeToken<List<StudentResponse>>() {
        }.getType());
    }

    @Override
    public List<StudentResponse> findAllByGroupId(Long groupId) {
        return modelMapper.map(studentRepo.findAllByGroupId(groupId), new TypeToken<List<StudentResponse>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void update(Long id, UpdateStudentRequest updated) {
        Student student = findStudentById(id);
        userService.update(id, updated);

        if (updated.getGroupId() != null) {
            student.setGroup(findGroupById(updated.getGroupId()));
        }
        studentRepo.save(student);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Student student = findStudentById(id);
        studentRepo.delete(student);
    }

    @Override
    @Transactional
    public StudentResponse assignToGroup(Long studentId, Long groupId) {
        Student student = findStudentById(studentId);
        Group group = findGroupById(groupId);
        student.setGroup(group);
        return modelMapper.map(studentRepo.save(student), StudentResponse.class);
    }

    private Student findStudentById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found id: " + id));
    }

    private Group findGroupById(Long groupId) {
        return groupRepo.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found: " + groupId));
    }
}
