package com.oliver.portfolio.model;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import jakarta.persistence.*;

@Entity
public class Skill {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  private String name;
  private String description;
  private int progress;
  
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  public Skill() {
  }
  
  public Skill(String name, String description, int progress, User user) {
    this.name = name;
    this.description = description;
    this.progress = progress;
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
  
  public int getProgress() {
    return progress;
  }
  
  public void setProgress(int progress) {
    this.progress = progress;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public SkillDetailDto skillDetailDtoToEntity(Skill skill, User user) {
    return new SkillDetailDto(skill, user);
  }
}
