package ua.edu.ifntuog.studentportal.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import ua.edu.ifntuog.studentportal.dto.*;
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
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserResponse save(CreateUserRequest dto) {
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
        return modelMapper.map(userRepo.save(user), UserResponse.class);
    }

    @Override
    public UserResponse findById(Long id) {
        return modelMapper.map(findUserById(id), UserResponse.class);
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public List<UserResponse> findAll() {
        return modelMapper.map(userRepo.findAll(), new TypeToken<List<UserResponse>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void update(Long id, UpdateUserRequest updated) {
        User user = findUserById(id);
        updateUserFromDto(updated, user);
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = findUserById(id);
        userRepo.delete(user);
    }

    @Override
    @Transactional
    public void addRole(Long userId, RoleType roleType) {
        User user = findUserById(userId);
        Role role = findRoleByName(roleType);
        if (!user.getRoles().add(role)) {
            throw new EntityAlreadyExistsException("User already has role: " + roleType);
        }
    }

    @Override
    @Transactional
    public void removeRole(Long userId, RoleType roleType) {
        User user = findUserById(userId);
        Role role = findRoleByName(roleType);
        if (!user.getRoles().remove(role)) {
            throw new EntityNotFoundException("User does not have role: " + roleType);
        }
    }

    private User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private Role findRoleByName(RoleType name) {
        return roleRepo.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + name));
    }

    private void updateUserFromDto(UpdateUserRequest dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
    }
}
