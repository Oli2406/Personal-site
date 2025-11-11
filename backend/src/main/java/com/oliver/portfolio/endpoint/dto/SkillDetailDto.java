package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.model.User;

public class SkillDetailDto {
  private int id;
  private String name;
  private String description;
  private int progress;
  private User user;
  
  public SkillDetailDto() {}
  
  public SkillDetailDto(Skill skill, User user) {
    this.id = skill.getId();
    this.name = skill.getName();
    this.description = skill.getDescription();
    this.progress = skill.getProgress();
    this.user = user;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getProgress() {
    return progress;
  }
  
  public void setProgress(int progress) {
    this.progress = progress;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
}
