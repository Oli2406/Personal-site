package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageSearchDto {
  private Long roomId;
  private String roomCode;
  private String query;
  private List<Message> results;
  
  // Constructor for single room search
  public MessageSearchDto(Long roomId, String query, List<Message> results) {
    this.roomId = roomId;
    this.query = query;
    this.results = results;
  }
  
  // Constructor for multi-room search
  public MessageSearchDto(Long roomId, String roomCode, String query, List<Message> results) {
    this.roomId = roomId;
    this.roomCode = roomCode;
    this.query = query;
    this.results = results;
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
  
  public List<Message> getResults() {
    return results;
  }

  public void setResults(List<Message> results) {
    this.results = results;
  }
  
  public void addResult(Message message) {
    this.results.add(message);
  }
}
