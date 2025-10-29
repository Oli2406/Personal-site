package com.oliver.portfolio.service;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;

import java.util.List;

public interface SkillService {
  
  public void deleteSkill(Long id);
  
  public List<SkillDetailDto> getAllSkills();
  
  public SkillDetailDto createSkill(SkillDetailDto skill);
}