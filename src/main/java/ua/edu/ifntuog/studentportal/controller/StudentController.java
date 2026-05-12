package ua.edu.ifntuog.studentportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ifntuog.studentportal.dto.response.StudentResponse;
import ua.edu.ifntuog.studentportal.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> save(
            @RequestParam Long userId,
            @RequestParam Long groupId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.save(userId, groupId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<StudentResponse>> findAllByGroupId(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(studentService.findAllByGroupId(groupId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{studentId}/group/{groupId}")
    public ResponseEntity<StudentResponse> assignToGroup(
            @PathVariable Long studentId,
            @PathVariable Long groupId) {
        return ResponseEntity.ok(studentService.assignToGroup(studentId, groupId));
    }
}
