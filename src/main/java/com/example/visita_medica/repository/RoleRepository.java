package com.example.visita_medica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.visita_medica.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
  Optional<Role> findByName(String name);
}
