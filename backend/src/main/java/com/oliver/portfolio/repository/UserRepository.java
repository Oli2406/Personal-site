package com.oliver.portfolio.repository;

import com.oliver.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  User findByUsername(String userName) throws UsernameNotFoundException;
  
  boolean existsByUsername(String userName);
  
}
