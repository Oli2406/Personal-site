package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.endpoint.dto.UserDetailDto;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.UserRepository;
import com.oliver.portfolio.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
  
  UserRepository userRepository;
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public AdminServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  public List<UserDetailDto> getAllUsers() {
    LOGGER.info("getAllUsers");
    List<User> users = userRepository.findAll();
    List<UserDetailDto> userDetailDtos = new ArrayList<>();
    for (User user : users) {
      UserDetailDto userDetailDto = new UserDetailDto();
      userDetailDto.setId(user.getId());
      userDetailDto.setUserName(user.getUsername());
      userDetailDto.setRole(user.getRole());
      userDetailDtos.add(userDetailDto);
    }
    return userDetailDtos;
  }
}
