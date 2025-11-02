package com.oliver.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "messages")
public class Message {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String sender;
  
  private String content;
  
  private Instant timestamp = Instant.now();
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private ChatRoom room;
  
  public Message(){}
  
  public Message(String sender, String content, ChatRoom room) {
    this.sender = sender;
    this.content = content;
    this.room = room;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
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
  
  public ChatRoom getRoom() {
    return room;
  }
  
  public void setRoom(ChatRoom room) {
    this.room = room;
  }
}
