package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.model.Skill;

public class SkillDetailDto {
  private String name;
  private String description;
  private int progress;
  
  public SkillDetailDto() {}
  
  public SkillDetailDto(Skill skill) {
    this.name = skill.getName();
    this.description = skill.getDescription();
    this.progress = skill.getProgress();
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
}
