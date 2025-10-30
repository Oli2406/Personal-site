package com.oliver.portfolio.service.validator;

import com.oliver.portfolio.endpoint.dto.LoginRequestDto;
import com.oliver.portfolio.endpoint.dto.RegisterRequestDto;
import com.oliver.portfolio.exception.ConflictException;
import com.oliver.portfolio.exception.ValidationException;
import com.oliver.portfolio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthValidator {
  
  private final UserRepository userRepository;
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public AuthValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  public void validateRegister(RegisterRequestDto register) throws ConflictException {
    LOGGER.info("Validating register data {}", register);
    List<String> errors = new ArrayList<>();
    
    if(userRepository.existsByUsername(register.getUsername())) {
      errors.add("Username is already in use");
    }
    
    if(!errors.isEmpty()) {
      LOGGER.warn(errors.toString());
      throw new ConflictException("Conflict detected", errors);
    }
  }
  
  public void validateLogin(LoginRequestDto login) throws ValidationException {
    LOGGER.info("Validating login data {}", login);
    List<String> errors = new ArrayList<>();
    
    if(!userRepository.existsByUsername(login.getUsername())) {
      errors.add("The username you entered is not registered");
    }
    
    if(!errors.isEmpty()) {
      LOGGER.warn(errors.toString());
      throw new ValidationException("Login validation failed", errors);
    }
  }
}
