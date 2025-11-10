package com.oliver.portfolio.endpoint.dto;

public class SkillProgressDto {
  private Long id;
  private String name;
  private String description;
  private int level;
  
  public SkillProgressDto(Long id, String name, String description, int level) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.level = level;
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
}
