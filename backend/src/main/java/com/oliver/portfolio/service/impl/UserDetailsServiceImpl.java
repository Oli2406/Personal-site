package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.UserRepository;
import com.oliver.portfolio.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  
  private final UserRepository userRepository;
  
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    return new UserPrincipal(user);
  }
}
