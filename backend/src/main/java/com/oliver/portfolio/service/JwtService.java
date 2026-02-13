package com.oliver.portfolio.service;

import com.oliver.portfolio.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
  public String extractUsername(String token);
  
  Long extractUserId(String token);
  
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
  
  public String generateToken(User user);
  
  public boolean validateToken(String token, UserDetails userDetails);
}
