package com.oliver.portfolio.service;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.model.User;

import java.util.List;

public interface SkillService {
  
  void deleteSkill(Long id);
  
  List<SkillDetailDto> getAllSkills();
  
  List<SkillDetailDto> getAllSkillsById(User user);
  
  SkillDetailDto createOrUpdateSkill(SkillDetailDto skill, User user);
}