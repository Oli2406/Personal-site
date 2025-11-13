package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.model.User;

public class SkillUpdateDto {
  private int id;
  private String name;
  private String description;
  private User user;
  private int sessionTime;
  
  public SkillUpdateDto(int id, String name, String description, User user, int sessionTime) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.sessionTime = sessionTime;
    this.user = user;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public int getSessionTime() {
    return sessionTime;
  }
  
  public void setSessionTime(int sessionTime) {
    this.sessionTime = sessionTime;
  }
}
