package ua.edu.ifntuog.studentportal.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ifntuog.studentportal.dto.*;
import ua.edu.ifntuog.studentportal.dto.request.CreateUserRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateUserRequest;
import ua.edu.ifntuog.studentportal.dto.response.UserResponse;
import ua.edu.ifntuog.studentportal.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable @NotNull Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        userService.update(id, request);

        return ResponseEntity.noContent().build();
    }
}
