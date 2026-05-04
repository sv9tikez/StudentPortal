package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.entity.Course;
import ua.edu.ifntuog.studentportal.repository.CourseRepo;
import ua.edu.ifntuog.studentportal.service.CourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;

    @Override
    public Course create(Course course) {
        return courseRepo.save(course);
    }

    @Override
    public Course getById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + id));
    }

    @Override
    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    @Override
    public List<Course> getAllByGroupId(Long groupId) {
        return courseRepo.findAllByGroupId(groupId);
    }

    @Override
    public List<Course> getAllByProfessorId(Long professorId) {
        return courseRepo.findAllByProfessorId(professorId);
    }

    @Override
    public List<Course> getAllBySubjectId(Long subjectId) {
        return courseRepo.findAllBySubjectId(subjectId);
    }

    @Override
    @Transactional
    public Course update(Long id, Course updated) {
        Course course = getById(id);
        course.setYear(updated.getYear());
        course.setSubject(updated.getSubject());
        course.setProfessor(updated.getProfessor());
        course.setGroup(updated.getGroup());
        return courseRepo.save(course);
    }

    @Override
    public void delete(Long id) {
        Course course = getById(id);
        courseRepo.delete(course);
    }
}
