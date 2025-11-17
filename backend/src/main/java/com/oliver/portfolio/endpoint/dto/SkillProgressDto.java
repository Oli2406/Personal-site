package com.oliver.portfolio.endpoint.dto;

import java.time.LocalDateTime;

public class SkillProgressDto {
  private Long id;
  private String name;
  private String description;
  private int level;
  private LocalDateTime createdAt;
  private int skillStreakDays = 0;
  
  public SkillProgressDto(Long id, String name, String description, int level, LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.level = level;
    this.createdAt = createdAt;
  }
  
  public SkillProgressDto() {}
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
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
  
  public int getLevel() {
    return level;
  }
  
  public void setLevel(int level) {
    this.level = level;
  }
  
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  
  public int getSkillStreakDays() {
    return skillStreakDays;
  }
  
  public void setSkillStreakDays(int skillStreakDays) {
    this.skillStreakDays = skillStreakDays;
  }
  
  @Override
  public String toString() {
    return("ID: " + id + "\nname: " + name + "\ndescription: " + description + "\nlevel: " + level + "\ncreatedAt: " + createdAt.toString() + "\nskillStreakDays: " + skillStreakDays);
  }
}
