package com.oliver.portfolio.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @Column(nullable = false, unique = true)
  private String code;
  
  private Instant createdAt = Instant.now();
  
  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Message> messages;
  
  public ChatRoom() {}
  
  public ChatRoom(String code) {
    this.code = code;
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public Instant getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
  
  public List<Message> getMessages() {
    return messages;
  }
  
  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }
}
