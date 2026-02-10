package com.oliver.portfolio.service;

import com.oliver.portfolio.endpoint.dto.AuthResponseDto;
import com.oliver.portfolio.endpoint.dto.LoginRequestDto;
import com.oliver.portfolio.endpoint.dto.RegisterRequestDto;
import com.oliver.portfolio.exception.ConflictException;
import com.oliver.portfolio.exception.ValidationException;

public interface AuthService {
  
  AuthResponseDto register(RegisterRequestDto request) throws ConflictException;
  
  AuthResponseDto login(LoginRequestDto request) throws ValidationException;
}
