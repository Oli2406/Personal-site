package com.oliver.portfolio.repository;

import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
  
  List<Skill> findByUserId(Long userId);
  
  Skill findByNameAndUser(String name, User user) throws EntityNotFoundException;
}
