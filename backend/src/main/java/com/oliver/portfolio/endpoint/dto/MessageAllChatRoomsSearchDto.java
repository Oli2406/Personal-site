package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.model.Message;

public class MessageAllChatRoomsSearchDto {
  
  private Long roomId;
  private String query;
  private Message message;
  
  public MessageAllChatRoomsSearchDto(Long roomId, String query, Message message) {
    this.roomId = roomId;
    this.query = query;
    this.message = message;
  }
  
  public Long getRoomId() {
    return roomId;
  }
  
  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }
  
  public Message getMessage() {
    return message;
  }
  
  public void setMessage(Message message) {
    this.message = message;
  }
  
  public String getQuery() {
    return query;
  }
  
  public void setQuery(String query) {
    this.query = query;
  }
}
