package ua.edu.ifntuog.studentportal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ifntuog.studentportal.dto.request.CourseRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateCourseRequest;
import ua.edu.ifntuog.studentportal.dto.response.CourseResponse;
import ua.edu.ifntuog.studentportal.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> findAll() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/by-group/{groupId}")
    public ResponseEntity<List<CourseResponse>> findAllByGroupId(@PathVariable Long groupId) {
        return ResponseEntity.ok(courseService.findAllByGroupId(groupId));
    }

    @GetMapping("/by-professor/{professorId}")
    public ResponseEntity<List<CourseResponse>> findAllByProfessorId(@PathVariable Long professorId) {
        return ResponseEntity.ok(courseService.findAllByProfessorId(professorId));
    }

    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<List<CourseResponse>> findAllBySubjectId(@PathVariable Long subjectId) {
        return ResponseEntity.ok(courseService.findAllBySubjectId(subjectId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody UpdateCourseRequest dto) {
        courseService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
