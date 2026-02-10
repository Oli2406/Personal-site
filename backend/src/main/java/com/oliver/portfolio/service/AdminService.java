package com.oliver.portfolio.service;

import com.oliver.portfolio.endpoint.dto.UserDetailDto;

import java.util.List;

public interface AdminService {
  
  List<UserDetailDto> getAllUsers();
}
