package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.CreateUserRequest;
import ua.edu.ifntuog.studentportal.entity.Role;
import ua.edu.ifntuog.studentportal.entity.User;
import ua.edu.ifntuog.studentportal.enums.RoleType;
import ua.edu.ifntuog.studentportal.exception.EntityAlreadyExistsException;
import ua.edu.ifntuog.studentportal.repository.RoleRepo;
import ua.edu.ifntuog.studentportal.repository.UserRepo;
import ua.edu.ifntuog.studentportal.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    @Transactional
    public User create(CreateUserRequest dto) {

        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new EntityAlreadyExistsException("User with email " + dto.getEmail() + " already exists");
        }

        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .roles(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .build();

        return userRepo.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    public User getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public User update(Long id, User updated) {
        User user = getById(id);
        updateUserFromDto(updated, user);
        return user;
    }

    @Override
    public void delete(Long id) {
        User user = getById(id);
        userRepo.delete(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    @Transactional
    public boolean addRole(Long userId, RoleType roleType) {
        User user = getById(userId);

        Role role = roleRepo.findByName(roleType)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleType));
        return user.getRoles().add(role);
    }

    @Override
    @Transactional
    public boolean removeRole(Long userId, RoleType roleType) {
        User user = getById(userId);

        Role role = roleRepo.findByName(roleType)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleType));

        return user.getRoles().remove(role);
    }

    private void updateUserFromDto(User dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRoles(dto.getRoles());
    }
}
