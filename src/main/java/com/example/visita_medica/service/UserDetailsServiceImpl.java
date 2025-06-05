package com.example.visita_medica.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.visita_medica.entity.User;
import com.example.visita_medica.entity.Role;
import com.example.visita_medica.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail()) // usa l'email come username per coerenza
            .password(user.getPassword())
            .authorities(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
            .build();
  }
}
