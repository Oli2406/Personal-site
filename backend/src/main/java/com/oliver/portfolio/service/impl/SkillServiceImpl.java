package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.SkillRepository;
import com.oliver.portfolio.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {
  
  private final SkillRepository skillRepository;
  
  public SkillServiceImpl(SkillRepository skillRepository) {
    this.skillRepository = skillRepository;
  }
  
  @Override
  public List<SkillDetailDto> getAllSkills() {
    return skillRepository.findAll().stream()
        .map(skill -> new SkillDetailDto(skill, skill.getUser()))
        .collect(Collectors.toList());
  }
  
  @Override
  public List<SkillDetailDto> getAllSkillsById(User user) {
    return skillRepository.findByUserId(user.getId()).stream()
        .map(skill -> new SkillDetailDto(skill, user))
        .collect(Collectors.toList());
  }
  
  @Override
  public SkillDetailDto createSkill(SkillDetailDto skillDetailDto, User user) {
    Skill skillToSave = new Skill(
        skillDetailDto.getName(),
        skillDetailDto.getDescription(),
        skillDetailDto.getProgress(),
        user
    );
    
    Skill saved = skillRepository.save(skillToSave);
    return new SkillDetailDto(saved, user);
  }
  
  @Override
  public void deleteSkill(Long id) {
    skillRepository.deleteById(id);
  }
}
