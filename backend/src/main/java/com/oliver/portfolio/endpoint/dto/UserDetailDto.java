package com.oliver.portfolio.endpoint.dto;

import com.oliver.portfolio.enums.Role;
import com.oliver.portfolio.model.User;

public class UserDetailDto {
  private long id;
  private String userName;
  private Role role;
  
  public void mapUserToUserDetailDto(User user) {
    this.id = user.getId();
    this.userName = user.getUsername();
    this.role = user.getRole();
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getUserName() {
    return userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public Role getRole() {
    return role;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }
}
