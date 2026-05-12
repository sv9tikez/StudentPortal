package ua.edu.ifntuog.studentportal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ifntuog.studentportal.dto.request.GroupRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateGroupRequest;
import ua.edu.ifntuog.studentportal.dto.response.GroupResponse;
import ua.edu.ifntuog.studentportal.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponse> create(@Valid @RequestBody GroupRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> findAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<List<GroupResponse>> findAllByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok(groupService.findAllByDepartmentId(departmentId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateGroupRequest dto) {
        groupService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
