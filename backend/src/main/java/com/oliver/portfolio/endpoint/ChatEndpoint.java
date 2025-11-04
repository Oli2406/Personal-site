package com.oliver.portfolio.endpoint;

import com.oliver.portfolio.service.ChatService;
import com.oliver.portfolio.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(ChatEndpoint.BASE_PATH)
public class ChatEndpoint {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  private final ChatService chatService;
  private final JwtService jwtService;
  
  public static final String BASE_PATH = "/api/chat";
  
  
  public ChatEndpoint(final ChatService chatService, final JwtService jwtService) {
    this.chatService = chatService;
    this.jwtService = jwtService;
  }
  
  @GetMapping("/rooms")
  public ResponseEntity<List<String>> getJoinedRooms(@RequestHeader("Authorization") String authHeader) {
    LOGGER.info("Getting joined rooms");
    String token = authHeader.replace("Bearer ", "");
    String username = jwtService.extractUsername(token);
    
    return ResponseEntity.ok(chatService.getJoinedRooms(username));
  }
}
