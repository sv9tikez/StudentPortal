package ua.edu.ifntuog.studentportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ifntuog.studentportal.dto.request.UpdateProfessorRequest;
import ua.edu.ifntuog.studentportal.dto.response.ProfessorResponse;
import ua.edu.ifntuog.studentportal.service.ProfessorService;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorResponse> save(
            @RequestParam Long userId,
            @RequestParam Long departmentId,
            @RequestParam String academicTitle) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(professorService.save(userId, departmentId, academicTitle));
    }

    @GetMapping("/findById")
    public ResponseEntity<ProfessorResponse> findById(@RequestParam Long id) {
        return ResponseEntity.ok(professorService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponse>> findAll() {
        return ResponseEntity.ok(professorService.findAll());
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<List<ProfessorResponse>> getAllByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok(professorService.findAllByDepartmentId(departmentId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody UpdateProfessorRequest request) {
        professorService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/assign-department/{departmentId}")
    public ResponseEntity<ProfessorResponse> assignToDepartment(
            @PathVariable Long id,
            @PathVariable Long departmentId
    ) {
        return ResponseEntity.ok(professorService.assignToDepartment(id, departmentId));
    }
}
