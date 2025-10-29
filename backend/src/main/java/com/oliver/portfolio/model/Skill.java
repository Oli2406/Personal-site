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
  
  public Skill() {
  }
  
  public Skill(String name, String description, int progress) {
    this.name = name;
    this.description = description;
    this.progress = progress;
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
  
  
  
  public SkillDetailDto skillDetailDtoToEntity(Skill skill) {
    return new SkillDetailDto(skill);
  }
  
}
