package ua.edu.ifntuog.studentportal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ifntuog.studentportal.dto.request.FacultyRequest;
import ua.edu.ifntuog.studentportal.dto.response.FacultyResponse;
import ua.edu.ifntuog.studentportal.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping
    public ResponseEntity<FacultyResponse> create(@Valid @RequestBody FacultyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findById(id));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<FacultyResponse> getByName(@PathVariable String name) {
        return ResponseEntity.ok(facultyService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<FacultyResponse>> findAll() {
        return ResponseEntity.ok(facultyService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody FacultyRequest request) {
        facultyService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
