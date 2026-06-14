package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.edu.ifntuog.studentportal.dto.request.CreateUserRequest;
import ua.edu.ifntuog.studentportal.dto.request.UpdateUserRequest;
import ua.edu.ifntuog.studentportal.dto.response.UserResponse;
import ua.edu.ifntuog.studentportal.entity.Role;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.RoleRepo;
import ua.edu.ifntuog.studentportal.repository.UserRepo;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final Long USER_ID = 1L;
    private static final Long ROLE_ID = 5L;
    private static final String FIRST_NAME = "user_firstName";
    private static final String LAST_NAME = "user_lastName";
    private static final String EMAIL = "user@example.com";
    private static final String PASSWORD = "pass";

    private CreateUserRequest createRequest;
    private UpdateUserRequest updateRequest;
    private User user;
    private UserResponse userResponse;
    private Role role;

    @Mock
    private UserRepo userRepo;

    @Mock
    private RoleRepo roleRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        createRequest = new CreateUserRequest();
        createRequest.setFirstName(FIRST_NAME);
        createRequest.setLastName(LAST_NAME);
        createRequest.setEmail(EMAIL);
        createRequest.setPassword(PASSWORD);

        updateRequest = new UpdateUserRequest(FIRST_NAME, LAST_NAME, EMAIL);

        user = User.builder().id(USER_ID).email(EMAIL).roles(new HashSet<>()).build();
        userResponse = UserResponse.builder().id(USER_ID).email(EMAIL).build();

        role = new Role();
        role.setId(ROLE_ID);
        role.setName(RoleType.ROLE_STUDENT);
    }

    @Test
    void save_success() {
        User saved = User.builder().id(USER_ID).email(EMAIL).roles(new HashSet<>()).build();

        when(userRepo.existsByEmail(EMAIL)).thenReturn(false);
        when(passwordEncoder.encode(PASSWORD)).thenReturn("hashed");
        when(userRepo.save(any(User.class))).thenReturn(saved);
        when(modelMapper.map(saved, UserResponse.class)).thenReturn(userResponse);

        UserResponse result = userService.save(createRequest);

        assertEquals(USER_ID, result.getId());
        verify(userRepo).save(any(User.class));
    }

    @Test
    void save_failure_whenEmailExists() {
        when(userRepo.existsByEmail(EMAIL)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> userService.save(createRequest));
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void findById_success() {
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);

        UserResponse result = userService.findById(USER_ID);

        assertEquals(USER_ID, result.getId());
    }

    @Test
    void findById_failure_whenMissing() {
        when(userRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(USER_ID));
    }

    @Test
    void findByEmail_success() {
        when(userRepo.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);

        UserResponse result = userService.findByEmail(EMAIL);

        assertEquals(EMAIL, result.getEmail());
    }

    @Test
    void findByEmail_failure_whenMissing() {
        when(userRepo.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findByEmail(EMAIL));
    }

    @Test
    void findAll_success() {
        when(userRepo.findAll()).thenReturn(List.of(new User(), new User()));
        when(modelMapper.map(any(), any(Type.class))).thenReturn(List.of(new UserResponse(), new UserResponse()));

        List<UserResponse> results = userService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void findAll_failure_whenRepoFails() {
        when(userRepo.findAll()).thenThrow(new RuntimeException("db"));

        assertThrows(RuntimeException.class, () -> userService.findAll());
    }

    @Test
    void update_success() {
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));

        userService.update(USER_ID, updateRequest);

        verify(userRepo).save(user);
        assertEquals(FIRST_NAME, user.getFirstName());
    }

    @Test
    void update_failure_whenMissing() {
        when(userRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.update(USER_ID, updateRequest));
    }

    @Test
    void delete_success() {
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));

        userService.delete(USER_ID);

        verify(userRepo).delete(user);
    }

    @Test
    void delete_failure_whenMissing() {
        when(userRepo.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.delete(USER_ID));
    }

    @Test
    void addRole_success() {
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));
        when(roleRepo.findByName(RoleType.ROLE_STUDENT)).thenReturn(Optional.of(role));

        userService.addRole(USER_ID, RoleType.ROLE_STUDENT);

        assertTrue(user.getRoles().contains(role));
    }

    @Test
    void addRole_failure_whenAlreadyAdded() {
        user.getRoles().add(role);

        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));
        when(roleRepo.findByName(RoleType.ROLE_STUDENT)).thenReturn(Optional.of(role));

        assertThrows(EntityAlreadyExistsException.class, () -> userService.addRole(USER_ID, RoleType.ROLE_STUDENT));
    }
}
