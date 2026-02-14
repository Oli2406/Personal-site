package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.model.Message;

import java.time.Instant;

public class MessageAllChatRoomsSearchDto {
  
  private Long roomId;
  private String roomCode;
  private String query;
  private String sender;
  private String content;
  private Instant timestamp;
  
  public MessageAllChatRoomsSearchDto(Long roomId, String roomCode, String query, Message message) {
    this.roomId = roomId;
    this.roomCode = roomCode;
    this.query = query;
    this.sender = message.getSender();
    this.content = message.getContent();
    this.timestamp = message.getTimestamp();
  }
  
  public Long getRoomId() {
    return roomId;
  }
  
  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }
  
  public String getRoomCode() {
    return roomCode;
  }
  
  public void setRoomCode(String roomCode) {
    this.roomCode = roomCode;
  }
  
  public String getQuery() {
    return query;
  }
  
  public void setQuery(String query) {
    this.query = query;
  }
  
  public String getSender() {
    return sender;
  }
  
  public void setSender(String sender) {
    this.sender = sender;
  }
  
  public String getContent() {
    return content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public Instant getTimestamp() {
    return timestamp;
  }
  
  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }
}
