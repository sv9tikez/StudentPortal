package ua.edu.ifntuog.studentportal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ifntuog.studentportal.dto.request.SubjectRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateSubjectRequest;
import ua.edu.ifntuog.studentportal.dto.response.SubjectResponse;
import ua.edu.ifntuog.studentportal.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectResponse> create(@Valid @RequestBody SubjectRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> findAll() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<List<SubjectResponse>> findAllByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok(subjectService.findAllByDepartmentId(departmentId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateSubjectRequest dto) {
        subjectService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
