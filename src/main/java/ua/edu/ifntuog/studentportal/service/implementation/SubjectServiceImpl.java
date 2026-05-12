package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.request.SubjectRequest;
import ua.edu.ifntuog.studentportal.dto.response.SubjectResponse;
import ua.edu.ifntuog.studentportal.dto.request.UpdateSubjectRequest;
import ua.edu.ifntuog.studentportal.entity.Department;
import ua.edu.ifntuog.studentportal.entity.Subject;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.DepartmentRepo;
import ua.edu.ifntuog.studentportal.repository.SubjectRepo;
import ua.edu.ifntuog.studentportal.service.SubjectService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepo subjectRepo;
    private final DepartmentRepo departmentRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public SubjectResponse create(SubjectRequest dto) {

        if (subjectRepo.existsByName(dto.getName())) {
            throw new EntityAlreadyExistsException("Subject with name " + dto.getName() + " already exists");
        }

        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setCredits(dto.getCredits());
        subject.setHours(dto.getHours());
        subject.setDepartment(findDepartmentById(dto.getDepartmentId()));
        return modelMapper.map(subjectRepo.save(subject), SubjectResponse.class);
    }

    @Override
    public SubjectResponse findById(Long id) {
        return modelMapper.map(findSubjectById(id), SubjectResponse.class);
    }

    @Override
    public List<SubjectResponse> findAll() {
        return modelMapper.map(subjectRepo.findAll(), new TypeToken<List<SubjectResponse>>() {
        }.getType());
    }

    @Override
    public List<SubjectResponse> findAllByDepartmentId(Long departmentId) {
        return modelMapper.map(subjectRepo.findAllByDepartmentId(departmentId), new TypeToken<List<SubjectResponse>>() {
        }.getType());

    }

    @Override
    @Transactional
    public void update(Long id, UpdateSubjectRequest updated) {
        Subject subject = findSubjectById(id);

        if (updated.getName() != null) {
            subject.setName(updated.getName());
        }

        if (updated.getCredits() != null) {
            subject.setCredits(updated.getCredits());
        }

        if (updated.getHours() != null) {
            subject.setHours(updated.getHours());
        }

        if (updated.getDepartmentId() != null) {
            subject.setDepartment(findDepartmentById(updated.getDepartmentId()));
        }

        subjectRepo.save(subject);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Subject subject = findSubjectById(id);
        subjectRepo.delete(subject);
    }

    private Subject findSubjectById(Long id) {
        return subjectRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found: " + id));
    }

    private Department findDepartmentById(Long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
    }
}
