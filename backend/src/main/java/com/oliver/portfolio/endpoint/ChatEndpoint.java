package com.oliver.portfolio.endpoint;

import com.oliver.portfolio.endpoint.dto.MessageAllChatRoomsSearchDto;
import com.oliver.portfolio.endpoint.dto.MessageSearchDto;
import com.oliver.portfolio.service.ChatService;
import com.oliver.portfolio.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  @Secured("ROLE_USER")
  public ResponseEntity<List<String>> getJoinedRooms(@RequestHeader("Authorization") String authHeader) {
    LOGGER.info("Getting joined rooms");
    String token = authHeader.replace("Bearer ", "");
    String username = jwtService.extractUsername(token);
    
    return ResponseEntity.ok(chatService.getJoinedRooms(username));
  }
  
  @GetMapping("{roomId}/messages/search")
  @Secured("ROLE_USER")
  public ResponseEntity<MessageSearchDto> searchMessages(@RequestHeader("Authorization") String authHeader,
                                                         @PathVariable Long roomId,
                                                         @RequestParam String query) {
    LOGGER.info("Searching messages in room {} with query: {}", roomId, query);
    String token = authHeader.replace("Bearer ", "");
    String username = jwtService.extractUsername(token);
    
    return ResponseEntity.ok(chatService.searchMessages(roomId, query, username));
  }
  
  @GetMapping("/messages/search")
  @Secured("ROLE_USER")
  public ResponseEntity<List<MessageAllChatRoomsSearchDto>> searchMessagesAcrossJoinedRooms(@RequestHeader("Authorization") String authHeader,
                                                                                            @RequestParam String query) {
    LOGGER.info("Searching messages across joined rooms with query: {}", query);
    String token = authHeader.replace("Bearer ", "");
    String username = jwtService.extractUsername(token);
    
    return ResponseEntity.ok(chatService.searchMessagesAcrossJoinedRooms(query, username));
  }
}
