package com.example.visita_medica.service;

import com.example.visita_medica.dto.RoleDto;
import com.example.visita_medica.entity.Role;
import com.example.visita_medica.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleDto createRole(RoleDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        Role saved = roleRepository.save(role);
        return toDto(saved);
    }

    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return toDto(role);
    }

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public RoleDto updateRole(Long id, RoleDto dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(dto.getName());
        Role updated = roleRepository.save(role);
        return toDto(updated);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDto toDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
