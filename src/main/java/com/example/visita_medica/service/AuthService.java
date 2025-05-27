package com.example.visita_medica.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.visita_medica.dto.AuthResponse;
import com.example.visita_medica.dto.LoginRequest;
import com.example.visita_medica.dto.RegisterRequest;
import com.example.visita_medica.entity.User;
import com.example.visita_medica.repository.UserRepository;
import com.example.visita_medica.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authManager;
  private final JwtUtils jwtUtils;

  public User register(RegisterRequest request){
    User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    return userRepo.save(user);
  }

  public AuthResponse login(LoginRequest request) {
      User user = userRepo.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Email non trovata"));

      if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
          throw new BadCredentialsException("Password errata");
      }

      String token = jwtUtils.generateJwtToken(user.getUsername());
      return new AuthResponse(token);
  }

  public void deleteUserById(Long id) {
      userRepo.deleteById(id);
  }

  public void deleteUserByUsername(String username) {
      userRepo.findByUsername(username).ifPresent(userRepo::delete);
  }
}