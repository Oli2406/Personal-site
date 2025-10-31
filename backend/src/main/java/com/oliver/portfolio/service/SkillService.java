package com.oliver.portfolio.service;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.model.User;

import java.util.List;

public interface SkillService {
  
  public void deleteSkill(Long id);
  
  public List<SkillDetailDto> getAllSkills();
  
  List<SkillDetailDto> getAllSkillsById(User user);
  
  public SkillDetailDto createSkill(SkillDetailDto skill, User user);
}