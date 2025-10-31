package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.endpoint.dto.AuthResponseDto;
import com.oliver.portfolio.endpoint.dto.LoginRequestDto;
import com.oliver.portfolio.endpoint.dto.RegisterRequestDto;
import com.oliver.portfolio.enums.Role;
import com.oliver.portfolio.exception.ConflictException;
import com.oliver.portfolio.exception.ValidationException;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.UserRepository;
import com.oliver.portfolio.service.AuthService;
import com.oliver.portfolio.service.JwtService;
import com.oliver.portfolio.service.validator.AuthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final AuthValidator authValidator;
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public AuthServiceImpl(UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         JwtService jwtService,
                         AuthenticationManager authenticationManager,
                         AuthValidator authValidator) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.authValidator = authValidator;
  }
  
  @Override
  public AuthResponseDto register(RegisterRequestDto request) throws ConflictException {
    authValidator.validateRegister(request);
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.valueOf(request.getRole().toUpperCase()));
    
    userRepository.save(user);
    
    String token = jwtService.generateToken(user);
    return new AuthResponseDto(token, user.getUsername(), user.getRole(), user.getId());
  }
  
  @Override
  public AuthResponseDto login(LoginRequestDto request) throws ValidationException {
    authValidator.validateLogin(request);
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );
    User user = userRepository.findByUsername(request.getUsername());
    
    LOGGER.info("Fetched user {}", user);
    
    String token = jwtService.generateToken(user);
    return new AuthResponseDto(token, user.getUsername(), user.getRole(), user.getId());
  }
}
