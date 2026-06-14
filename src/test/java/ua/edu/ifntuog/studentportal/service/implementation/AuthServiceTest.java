package ua.edu.ifntuog.studentportal.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ua.edu.ifntuog.studentportal.security.JwtUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private static final String EMAIL = "user@example.com";
    private static final String PASSWORD = "pass";

    private Authentication auth;
    private User userDetails;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        auth = mock(Authentication.class);
        userDetails = new User(EMAIL, PASSWORD, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void login_success() {
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(jwtUtil.generateToken(eq(EMAIL), anyList())).thenReturn("token");

        String token = authService.login(EMAIL, PASSWORD);

        assertEquals("token", token);
        verify(jwtUtil).generateToken(eq(EMAIL), anyList());
    }

    @Test
    void login_failure_whenBadCredentials() {
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("bad"));

        assertThrows(BadCredentialsException.class, () -> authService.login(EMAIL, "bad_password"));
    }
}
