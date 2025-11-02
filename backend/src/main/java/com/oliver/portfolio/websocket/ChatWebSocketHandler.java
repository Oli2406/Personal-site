package com.oliver.portfolio.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliver.portfolio.service.JwtService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
  
  private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();
  private final ObjectMapper mapper = new ObjectMapper();
  private final JwtService jwtService;
  
  public ChatWebSocketHandler(JwtService jwtService) {
    this.jwtService = jwtService;
  }
  
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    String room = getQueryParam(session, "room");
    String token = getQueryParam(session, "token");
    
    if (room == null || token == null) {
      session.close(CloseStatus.BAD_DATA);
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
    session.getAttributes().put("token", token);
    
    rooms.computeIfAbsent(room, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
    
    broadcast(room, Map.of(
        "type", "NEW_MESSAGE",
        "message", Map.of(
            "sender", "System",
            "content", username + " joined the room.",
            "timestamp", Instant.now().toString()
        )
    ));
    
    sendRoomInfo(session, room);
  }
  
  
  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    Map<String, Object> msg = mapper.readValue(message.getPayload(), Map.class);
    String type = (String) msg.get("type");
    
    if ("MESSAGE".equals(type)) {
      String room = (String) msg.get("room");
      String sender = (String) session.getAttributes().get("username");
      
      broadcast(room, Map.of(
          "type", "NEW_MESSAGE",
          "message", Map.of(
              "sender", sender,
              "content", msg.get("content"),
              "timestamp", Instant.now().toString()
          )
      ));
    }
  }
  
  
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    String room = getQueryParam(session, "room");
    if (room == null) return;
    
    Set<WebSocketSession> participants = rooms.get(room);
    if (participants != null) {
      participants.remove(session);
      if (participants.isEmpty()) rooms.remove(room);
      broadcast(room, Map.of(
          "type", "NEW_MESSAGE",
          "message", Map.of(
              "sender", "System",
              "content", "A user left the room.",
              "timestamp", Instant.now().toString()
          )
      ));
    }
  }
  
  private void broadcast(String room, Map<String, Object> message) throws Exception {
    String json = mapper.writeValueAsString(message);
    Set<WebSocketSession> participants = rooms.get(room);
    if (participants == null) return;
    
    synchronized (participants) {
      for (WebSocketSession s : participants) {
        if (s.isOpen()) s.sendMessage(new TextMessage(json));
      }
    }
  }
  
  private String getQueryParam(WebSocketSession session, String key) {
    if (session.getUri() == null || session.getUri().getQuery() == null) return null;
    
    String[] params = session.getUri().getQuery().split("&");
    for (String param : params) {
      int idx = param.indexOf("=");
      if (idx > 0) {
        String k = param.substring(0, idx);
        String v = param.substring(idx + 1);
        if (k.equals(key)) {
          return URLDecoder.decode(v, StandardCharsets.UTF_8);
        }
      }
    }
    return null;
  }
  
  private void sendRoomInfo(WebSocketSession session, String room) throws Exception {
    Set<WebSocketSession> participants = rooms.getOrDefault(room, Collections.emptySet());
    List<String> tokens = new ArrayList<>();
    synchronized (participants) {
      for (WebSocketSession s : participants) {
        String t = getQueryParam(s, "token");
        String username = jwtService.extractUsername(t);
        if (t != null) tokens.add(username);
      }
    }
    
    Map<String, Object> roomInfo = Map.of(
        "type", "ROOM_INFO",
        "participants", tokens,
        "messages", List.of()
    );
    if (session.isOpen()) session.sendMessage(new TextMessage(mapper.writeValueAsString(roomInfo)));
  }
}
