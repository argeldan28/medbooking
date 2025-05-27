package com.example.visita_medica.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
  
  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(String username) {
      return Jwts.builder()
              .setSubject(username)
              .setIssuedAt(new Date())
              .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
              .signWith(SignatureAlgorithm.HS512, jwtSecret)
              .compact();
  }

  public boolean validateJwtToken(String authToken) {
      try {
          Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
          return true;
      } catch (Exception e) {
          // Errore
      }
      return false;
  }
}
