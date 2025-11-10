package com.oliver.portfolio.service;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.endpoint.dto.SkillProgressDto;
import com.oliver.portfolio.model.SkillProgress;
import com.oliver.portfolio.model.User;

import java.util.List;

public interface SkillProgressService {
  
  SkillProgress save(SkillDetailDto skill, User user);
  
  List<SkillProgressDto> getAllUpdatesById(Long id);
}
