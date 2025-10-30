package com.oliver.portfolio.service;

import com.oliver.portfolio.endpoint.dto.AuthResponseDto;
import com.oliver.portfolio.endpoint.dto.LoginRequestDto;
import com.oliver.portfolio.endpoint.dto.RegisterRequestDto;
import com.oliver.portfolio.enums.Role;
import com.oliver.portfolio.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthService {
  
  public AuthResponseDto register(RegisterRequestDto request);
  
  public AuthResponseDto login(LoginRequestDto request);
}
