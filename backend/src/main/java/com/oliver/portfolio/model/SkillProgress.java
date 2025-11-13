package com.oliver.portfolio.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "skill_progress")
public class SkillProgress {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "skill_id", nullable = false)
  private Skill skill;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  private int sessionTimeMinutes;
  
  @Column(length = 500)
  private String note;
  
  private LocalDateTime timestamp = LocalDateTime.now();
  
  public SkillProgress() {}
  
  public SkillProgress(Skill skill, User user, String note, int sessionTimeMinutes) {
    this.skill = skill;
    this.user = user;
    this.note = note;
    this.timestamp = LocalDateTime.now();
    this.sessionTimeMinutes = sessionTimeMinutes;
  }
  
  public Long getId() {
    return id;
  }
  
  public Skill getSkill() {
    return skill;
  }
  
  public void setSkill(Skill skill) {
    this.skill = skill;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public String getNote() {
    return note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
  public LocalDateTime getTimestamp() {
    return timestamp;
  }
  
  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public int getSessionTimeMinutes() {
    return sessionTimeMinutes;
  }
  
  public void setSessionTimeMinutes(int sessionTimeMinutes) {
    this.sessionTimeMinutes = sessionTimeMinutes;
  }
}
