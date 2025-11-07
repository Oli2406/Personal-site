package com.oliver.portfolio.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.oliver.portfolio.endpoint.dto.MessageDto;
import com.oliver.portfolio.endpoint.dto.RoomInfoDto;
import com.oliver.portfolio.exception.ValidationException;
import com.oliver.portfolio.model.ChatRoom;
import com.oliver.portfolio.model.Message;
import com.oliver.portfolio.repository.ChatRoomMemberRepository;
import com.oliver.portfolio.repository.ChatRoomRepository;
import com.oliver.portfolio.repository.UserRepository;
import com.oliver.portfolio.service.ChatService;
import com.oliver.portfolio.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.lang.invoke.MethodHandles;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
  
  private final ChatService chatService;
  private final JwtService jwtService;
  private final ObjectMapper mapper = new ObjectMapper();
  private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();;
  private final UserRepository userRepository;
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private final ChatRoomRepository chatRoomRepository;
  private final ChatRoomMemberRepository chatRoomMemberRepository;
  
  public ChatWebSocketHandler(JwtService jwtService,
                              ChatService chatService,
                              UserRepository userRepository, ChatRoomRepository chatRoomRepository, ChatRoomMemberRepository chatRoomMemberRepository) {
    this.jwtService = jwtService;
    this.chatService = chatService;
    
    this.mapper.registerModule(new JavaTimeModule());
    this.mapper.registerModule(new Jdk8Module());
    this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    this.mapper.findAndRegisterModules();
    this.userRepository = userRepository;
    this.chatRoomRepository = chatRoomRepository;
    this.chatRoomMemberRepository = chatRoomMemberRepository;
  }
  
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    String roomCode = getQueryParam(session, "room");
    String token = getQueryParam(session, "token");
    
    if (roomCode == null || token == null) {
      session.close(CloseStatus.BAD_DATA.withReason("Missing parameters"));
      return;
    }
    
    String username;
    try {
      username = jwtService.extractUsername(token);
    } catch (Exception e) {
      session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Invalid JWT"));
      return;
    }
    
    session.getAttributes().put("username", username);
    session.getAttributes().put("room", roomCode);
    
    ChatRoom room = chatService.getOrCreate(roomCode);
    rooms.computeIfAbsent(roomCode, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
    
    List<MessageDto> history = chatService.getMessageAfterJoin(room, username).stream()
        .map(m -> new MessageDto(m.getSender(), m.getContent(), m.getTimestamp()))
        .collect(Collectors.toList());
    
    RoomInfoDto roomInfo = new RoomInfoDto(roomCode, getUsernames(roomCode), history);
    Map<String, Object> payload = Map.of("type", "ROOM_INFO", "data", roomInfo);
    session.sendMessage(new TextMessage(mapper.writeValueAsString(payload)));
    
    boolean isUserAlreadyJoined = chatService.isUserInRoom(roomCode, username);
    
    if(!isUserAlreadyJoined) {
      Message joinMessage = chatService.save("System", username + " joined the room.", room);
      MessageDto joinDto = new MessageDto(joinMessage.getSender(), joinMessage.getContent(), joinMessage.getTimestamp());
      broadcast(roomCode, Map.of("type", "NEW_MESSAGE", "message", joinDto));
      chatService.addUserToRoom(userRepository.findByUsername(username), room);
    }
  }
  
  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage rawMessage) throws Exception {
    try {
      Map<String, Object> msg = mapper.readValue(rawMessage.getPayload(), Map.class);
      String type = (String) msg.get("type");
      
      if (type == null) return;
      
      String roomCode = (String) msg.get("room");
      String sender = (String) session.getAttributes().get("username");
      String content = (String) msg.get("content");
      
      switch (type) {
        case "MESSAGE" -> {
          if (roomCode == null || sender == null || content == null || content.isBlank()) return;
          
          ChatRoom room = chatService.getOrCreate(roomCode);
          Message saved = chatService.save(sender, content, room); // may throw ValidationException
          
          MessageDto dto = new MessageDto(saved.getSender(), saved.getContent(), saved.getTimestamp());
          broadcast(roomCode, Map.of("type", "NEW_MESSAGE", "message", dto));
        }
        case "LEAVE" -> {
          ChatRoom room = chatService.getOrCreate(roomCode);
          Set<WebSocketSession> participants = rooms.get(roomCode);
          if (participants != null) {
            participants.remove(session);
          }
          
          chatService.removeUserFromRoom(userRepository.findByUsername(sender), room);
          
          Message leaveMessage = chatService.save("System", sender + " left the room", room);
          MessageDto leaveDto = new MessageDto(
              leaveMessage.getSender(), leaveMessage.getContent(), leaveMessage.getTimestamp()
          );
          
          broadcast(roomCode, Map.of("type", "NEW_MESSAGE", "message", leaveDto));
          broadcast(roomCode, Map.of("type", "PARTICIPANTS_UPDATE", "participants", getUsernames(roomCode)));
          session.close(CloseStatus.NORMAL);
        }
      }
      
    } catch (ValidationException e) {
      LOGGER.warn("Validation error: {}", e.getErrors());
      sendError(session, String.join(", ", e.getErrors()));
    } catch (Exception e) {
      LOGGER.error("Error handling WebSocket message", e);
      sendError(session, "Unexpected error: " + e.getMessage());
    }
  }
  
  
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    String roomCode = (String) session.getAttributes().get("room");
    
    if (roomCode == null) return;
    
    Set<WebSocketSession> participants = rooms.get(roomCode);
    if (participants != null) {
      participants.remove(session);
      if (participants.isEmpty()) rooms.remove(roomCode);
    }
  }
  
  private void broadcast(String roomCode, Map<String, Object> message) throws Exception {
    String json = mapper.writeValueAsString(message);
    Set<WebSocketSession> participants = rooms.get(roomCode);
    if (participants == null) return;
    
    synchronized (participants) {
      for (WebSocketSession s : participants) {
        if (s.isOpen()) s.sendMessage(new TextMessage(json));
      }
    }
  }
  
  private List<String> getUsernames(String roomCode) {
    ChatRoom room = chatRoomRepository.findByCode(roomCode)
        .orElseThrow(() -> new RuntimeException("Room not found"));
    
    return chatRoomMemberRepository.findAllByRoom(room)
        .stream()
        .map(member -> member.getUser().getUsername())
        .toList();
  }
  
  private String getQueryParam(WebSocketSession session, String key) {
    if (session.getUri() == null || session.getUri().getQuery() == null) return null;
    for (String param : session.getUri().getQuery().split("&")) {
      int idx = param.indexOf("=");
      if (idx > 0) {
        String k = param.substring(0, idx);
        String v = param.substring(idx + 1);
        if (k.equals(key)) return URLDecoder.decode(v, StandardCharsets.UTF_8);
      }
    }
    return null;
  }
  
  private void sendError(WebSocketSession session, String errorMessage) {
    try {
      Map<String, Object> payload = Map.of(
          "type", "ERROR",
          "message", errorMessage
      );
      session.sendMessage(new TextMessage(mapper.writeValueAsString(payload)));
    } catch (Exception ex) {
      LOGGER.error("Failed to send error message to client", ex);
    }
  }
}
