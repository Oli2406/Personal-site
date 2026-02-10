package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.enums.Role;
import com.oliver.portfolio.model.User;


public class UserDetailDto {
  private long id;
  private String username;
  private Role role;
  
  public void mapUserToUserDetailDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.role = user.getRole();
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getUserName() {
    return username;
  }
  
  public void setUserName(String username) {
    this.username = username;
  }
  
  public Role getRole() {
    return role;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }
}
