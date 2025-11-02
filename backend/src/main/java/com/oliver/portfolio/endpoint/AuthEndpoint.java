package com.oliver.portfolio.endpoint;

import com.oliver.portfolio.endpoint.dto.AuthResponseDto;
import com.oliver.portfolio.endpoint.dto.LoginRequestDto;
import com.oliver.portfolio.endpoint.dto.RegisterRequestDto;
import com.oliver.portfolio.exception.ConflictException;
import com.oliver.portfolio.exception.ValidationException;
import com.oliver.portfolio.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(AuthEndpoint.BASE_PATH)
public class AuthEndpoint {
  
  public static final String BASE_PATH = "/api/auth";
  
  private final AuthService authService;
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public AuthEndpoint(AuthService authService) {
    this.authService = authService;
  }
  
  @PostMapping("/register")
  public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) throws ConflictException {
    LOGGER.info("Register request: {}", request);
    request.setRole("user");
    return ResponseEntity.ok(authService.register(request));
  }
  
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto request) throws ValidationException {
    LOGGER.info("Login request: {}", request);
    return ResponseEntity.ok(authService.login(request));
  }
}