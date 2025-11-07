package com.oliver.portfolio.service.validator;

import com.oliver.portfolio.exception.ValidationException;
import com.oliver.portfolio.model.Message;
import com.oliver.portfolio.repository.ChatRoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageValidator {
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public void validateMessage(Message message) throws ValidationException {
    LOGGER.info("Validating message {}", message);
    
    List<String> errors = new ArrayList<>();
    
    if(message.getContent().length() > 255) {
      errors.add("Message content too long");
    }
    
    if(message.getContent().trim().isEmpty()) {
      errors.add("Message content is empty");
    }
    
    if(!errors.isEmpty()) {
      LOGGER.warn(errors.toString());
      throw new ValidationException("Message validation failed", errors);
    }
  }
}
