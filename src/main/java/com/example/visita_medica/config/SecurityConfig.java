package com.example.visita_medica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf().disable()
          .authorizeHttpRequests()
          .requestMatchers("/api/auth/**").permitAll() // auth ok
          .requestMatchers("/api/visits/**").permitAll()   // aggiungi queste
          .requestMatchers("/api/users/**").permitAll()
          .requestMatchers("/api/roles/**").permitAll()
          .anyRequest().authenticated();
      return http.build();
  }
}
