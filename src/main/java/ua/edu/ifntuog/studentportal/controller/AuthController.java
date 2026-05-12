package ua.edu.ifntuog.studentportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ifntuog.studentportal.service.implementation.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String token = authService.login(body.get("email"), body.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }
}
