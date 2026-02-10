package com.oliver.portfolio.repository;

import com.oliver.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  User findByUsername(String username) throws UsernameNotFoundException;
  
  boolean existsByUsername(String username);
  
}
