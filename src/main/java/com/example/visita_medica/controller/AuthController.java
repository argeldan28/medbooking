package com.example.visita_medica.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.visita_medica.dto.AuthResponse;
import com.example.visita_medica.dto.LoginRequest;
import com.example.visita_medica.dto.RegisterRequest;
import com.example.visita_medica.entity.User;
import com.example.visita_medica.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody RegisterRequest request){
    User savedUser = authService.register(request);
    return ResponseEntity.ok(savedUser);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
      AuthResponse response = authService.login(request);
      return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
      authService.deleteUserById(id);
      return ResponseEntity.noContent().build();
  }
}
