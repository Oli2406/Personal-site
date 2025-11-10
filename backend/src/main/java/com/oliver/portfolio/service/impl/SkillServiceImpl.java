package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.model.SkillProgress;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.SkillProgressRepository;
import com.oliver.portfolio.repository.SkillRepository;
import com.oliver.portfolio.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {
  
  private final SkillRepository skillRepository;
  
  private final SkillProgressRepository skillProgressRepository;
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public SkillServiceImpl(SkillRepository skillRepository,
                          SkillProgressRepository skillProgressRepository) {
    this.skillRepository = skillRepository;
    this.skillProgressRepository = skillProgressRepository;
  }
  
  @Override
  public List<SkillDetailDto> getAllSkills() {
    LOGGER.info("Getting all skills");
    return skillRepository.findAll().stream()
        .map(skill -> new SkillDetailDto(skill, skill.getUser()))
        .collect(Collectors.toList());
  }
  
  @Override
  public List<SkillDetailDto> getAllSkillsById(User user) {
    LOGGER.info("Getting all skills by user {}", user);
    return skillRepository.findByUserId(user.getId()).stream()
        .map(skill -> new SkillDetailDto(skill, user))
        .collect(Collectors.toList());
  }
  
  @Override
  public SkillDetailDto createSkill(SkillDetailDto skillDetailDto, User user) {
    LOGGER.info("Creating skill {}", skillDetailDto);
    Skill skillToSave = new Skill(
        skillDetailDto.getName(),
        skillDetailDto.getDescription(),
        skillDetailDto.getProgress(),
        user
    );
    
    SkillProgress progressToSave = new SkillProgress(
        skillToSave,
        user,
        skillDetailDto.getProgress(),
        skillDetailDto.getDescription()
    );
    
    skillToSave.addProgress(progressToSave);
    
    Skill saved = skillRepository.save(skillToSave);
    return new SkillDetailDto(saved, user);
  }
  
  @Override
  public void deleteSkill(Long id) {
    LOGGER.info("Deleting skill {}", id);
    skillRepository.deleteById(id);
  }
}
