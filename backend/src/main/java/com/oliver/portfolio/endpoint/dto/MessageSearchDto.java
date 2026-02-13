package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageSearchDto {
  private Long roomId;
  private String query;
  private List<Message> results;
  
  public MessageSearchDto(Long roomId, String query, List<Message> results) {
    this.roomId = roomId;
    this.query = query;
    this.results = results;
  }
  
  public Long getRoomId() {
    return roomId;
  }
  
  public void setRoomId(Long roomId) {
    this.roomId = roomId;
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
