package com.example.visita_medica.service;

import com.example.visita_medica.dto.UserDto;
import com.example.visita_medica.entity.Role;
import com.example.visita_medica.entity.User;
import com.example.visita_medica.repository.RoleRepository;
import com.example.visita_medica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDto createUser(UserDto dto) {
        Set<Role> roles = dto.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Ruolo non trovato: " + roleName)))
                .collect(Collectors.toSet());

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .roles(roles)
                .build();

        User saved = userRepository.save(user);
        return toDto(saved);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Role> roles = dto.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Ruolo non trovato: " + roleName)))
                .collect(Collectors.toSet());

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRoles(roles);

        User updated = userRepository.save(user);
        return toDto(updated);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto toDto(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleNames)
                .build();
    }
}
