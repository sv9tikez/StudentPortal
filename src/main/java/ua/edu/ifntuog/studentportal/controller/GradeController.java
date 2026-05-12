package ua.edu.ifntuog.studentportal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ifntuog.studentportal.dto.request.GradeRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateGradeRequest;
import ua.edu.ifntuog.studentportal.dto.response.GradeResponse;
import ua.edu.ifntuog.studentportal.service.GradeService;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeResponse> create(@Valid  @RequestBody GradeRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gradeService.findById(id));
    }

    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<List<GradeResponse>> findAllByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.findAllByStudentId(studentId));
    }

    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<GradeResponse>> findAllByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(gradeService.findAllByCourseId(courseId));
    }

    @GetMapping("/by-student/{studentId}/by-course/{courseId}")
    public ResponseEntity<List<GradeResponse>> findAllByStudentAndCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return ResponseEntity.ok(gradeService.findAllByStudentIdAndCourseId(studentId, courseId));
    }

    @GetMapping("/by-student/{studentId}/average")
    public ResponseEntity<Double> findAverageGradeByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.findAverageGradeByStudentId(studentId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateGradeRequest dto) {
        gradeService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gradeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
