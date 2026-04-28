package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.entity.Grade;
import ua.edu.ifntuog.studentportal.repository.GradeRepo;
import ua.edu.ifntuog.studentportal.service.GradeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepo gradeRepo;

    @Override
    public Grade create(Grade grade) {
        return gradeRepo.save(grade);
    }

    @Override
    public Grade getById(Long id) {
        return gradeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found: " + id));
    }

    @Override
    public List<Grade> getAllByStudentId(Long studentId) {
        return gradeRepo.findAllByStudentId(studentId);
    }

    @Override
    public List<Grade> getAllByCourseId(Long courseId) {
        return gradeRepo.findAllByCourseId(courseId);
    }

    @Override
    public List<Grade> getAllByStudentIdAndCourseId(Long studentId, Long courseId) {
        return gradeRepo.findAllByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    @Transactional
    public Grade update(Long id, Grade updated) {
        Grade grade = getById(id);
        grade.setGrade(updated.getGrade());
        grade.setType(updated.getType());
        grade.setDate(updated.getDate());
        return gradeRepo.save(grade);
    }

    @Override
    public void delete(Long id) {
        Grade grade = getById(id);
        gradeRepo.delete(grade);
    }

    @Override
    public Double getAverageGradeByStudentId(Long studentId) {
        return gradeRepo.findAllByStudentId(studentId).stream()
                .mapToInt(Grade::getGrade)
                .average()
                .orElse(0.0);
    }
}
