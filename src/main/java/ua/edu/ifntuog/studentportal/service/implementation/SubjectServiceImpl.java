package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.entity.Subject;
import ua.edu.ifntuog.studentportal.repository.SubjectRepo;
import ua.edu.ifntuog.studentportal.service.SubjectService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepo subjectRepo;

    @Override
    public Subject create(Subject subject) {
        return subjectRepo.save(subject);
    }

    @Override
    public Subject getById(Long id) {
        return subjectRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found: " + id));
    }

    @Override
    public List<Subject> getAll() {
        return subjectRepo.findAll();
    }

    @Override
    public List<Subject> getAllByDepartmentId(Long departmentId) {
        return subjectRepo.findAllByDepartmentId(departmentId);
    }

    @Override
    @Transactional
    public Subject update(Long id, Subject updated) {
        Subject subject = getById(id);
        subject.setName(updated.getName());
        subject.setCredits(updated.getCredits());
        subject.setHours(updated.getHours());
        subject.setDepartment(updated.getDepartment());
        return subjectRepo.save(subject);
    }

    @Override
    public void delete(Long id) {
        Subject subject = getById(id);
        subjectRepo.delete(subject);
    }
}
