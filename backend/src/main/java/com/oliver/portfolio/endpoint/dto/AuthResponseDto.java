package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.enums.Role;

public class AuthResponseDto {
  private String token;
  private String username;
  private Role role;
  private long id;
  
  public AuthResponseDto(String token, String username, Role role, Long id) {
    this.token = token;
    this.username = username;
    this.role = role;
    this.id = id;
  }
  
  public String getToken() {
    return token;
  }
  
  public void setToken(String token) {
    this.token = token;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public Role getRole() {
    return role;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
}
