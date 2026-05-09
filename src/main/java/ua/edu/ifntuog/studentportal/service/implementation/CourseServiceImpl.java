package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.request.CourseRequest;
import ua.edu.ifntuog.studentportal.dto.response.CourseResponse;
import ua.edu.ifntuog.studentportal.dto.request.UpdateCourseRequest;
import ua.edu.ifntuog.studentportal.entity.*;
import ua.edu.ifntuog.studentportal.exception.DuplicateCourseException;
import ua.edu.ifntuog.studentportal.repository.*;
import ua.edu.ifntuog.studentportal.service.CourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;
    private final SubjectRepo subjectRepo;
    private final ProfessorRepo professorRepo;
    private final GroupRepo groupRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CourseResponse create(CourseRequest dto) {
        checkForDuplicate(dto.getYear(), dto.getSubjectId(), dto.getProfessorId(), dto.getGroupId());

        Course course = new Course();
        course.setSubject(findSubjectById(dto.getSubjectId()));
        course.setProfessor(findProfessorById(dto.getProfessorId()));
        course.setGroup(findGroupById(dto.getGroupId()));
        return modelMapper.map(courseRepo.save(course), CourseResponse.class);
    }

    @Override
    public CourseResponse findById(Long id) {
        return modelMapper.map(findCourseById(id), CourseResponse.class);
    }

    @Override
    public List<CourseResponse> findAll() {
        return modelMapper.map(courseRepo.findAll(), new TypeToken<List<CourseResponse>>() {
        }.getType());
    }

    @Override
    public List<CourseResponse> findAllByGroupId(Long groupId) {
        return modelMapper.map(courseRepo.findAllByGroupId(groupId), new TypeToken<List<CourseResponse>>() {
        }.getType());
    }

    @Override
    public List<CourseResponse> findAllByProfessorId(Long professorId) {
        return modelMapper.map(courseRepo.findAllByProfessorId(professorId), new TypeToken<List<CourseResponse>>() {
        }.getType());
    }

    @Override
    public List<CourseResponse> findAllBySubjectId(Long subjectId) {
        return modelMapper.map(courseRepo.findAllBySubjectId(subjectId), new TypeToken<List<CourseResponse>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void update(Long id, UpdateCourseRequest updated) {
        Course course = findCourseById(id);

        if (updated.getYear() != null) {
            course.setYear(updated.getYear());
        }

        if (updated.getSubjectId() != null) {
            course.setSubject(findSubjectById(updated.getSubjectId()));
        }

        if (updated.getProfessorId() != null) {
            course.setProfessor(findProfessorById(updated.getProfessorId()));
        }

        if (updated.getGroupId() != null) {
            course.setGroup(findGroupById(updated.getGroupId()));
        }

        courseRepo.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Course course = findCourseById(id);
        courseRepo.delete(course);
    }

    private Course findCourseById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + id));
    }

    private Subject findSubjectById(Long id) {
        return subjectRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found: " + id));
    }

    private Professor findProfessorById(Long id) {
        return professorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + id));
    }

    private Group findGroupById(Long id) {
        return groupRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found: " + id));
    }

    private void checkForDuplicate(Integer year, Long subjectId, Long professorId, Long groupId) {
        if (courseRepo.existsByYearAndSubject_IdAndProfessor_IdAndGroup_Id(
                year, subjectId, professorId, groupId)) {
            throw new DuplicateCourseException(
                    "Course with year=%d, subjectId=%d, professorId=%d, groupId=%d already exists"
                            .formatted(year, subjectId, professorId, groupId));
        }
    }
}
