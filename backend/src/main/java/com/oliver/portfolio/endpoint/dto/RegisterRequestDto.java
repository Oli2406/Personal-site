package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.enums.Role;

public class RegisterRequestDto {
  private String username;
  private String password;
  private Role role;
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public Role getRole() {
    return role;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }
}
