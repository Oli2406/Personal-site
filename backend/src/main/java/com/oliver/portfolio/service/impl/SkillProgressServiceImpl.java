package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.endpoint.dto.SkillProgressDto;
import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.model.SkillProgress;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.SkillProgressRepository;
import com.oliver.portfolio.repository.SkillRepository;
import com.oliver.portfolio.repository.UserRepository;
import com.oliver.portfolio.service.SkillProgressService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillProgressServiceImpl implements SkillProgressService {
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  private final SkillProgressRepository skillProgressRepository;
  private final SkillRepository skillRepository;
  
  public SkillProgressServiceImpl(SkillProgressRepository skillProgressRepository,
                                  SkillRepository skillRepository) {
    this.skillProgressRepository = skillProgressRepository;
    this.skillRepository = skillRepository;
  }
  
  @Override
  public SkillProgress save(SkillDetailDto skill, User user) {
    LOGGER.info("Saving SkillProgress");
    
    Skill existingSkill = skillRepository.findByNameAndUser(skill.getName(), user);
    
    SkillProgress progress = new SkillProgress(
        existingSkill,
        user,
        skill.getProgress(),
        skill.getDescription()
    );
    
    return skillProgressRepository.save(progress);
  }
  
  @Override
  public List<SkillProgressDto> getAllUpdatesById(Long id) {
    LOGGER.info("Getting all SkillProgress updates for id {}", id);
    List<SkillProgress> skills = skillProgressRepository.findAllByUser_Id(id);
    return skills.stream()
        .map(skillProgress -> new SkillProgressDto(
            skillProgress.getId(),
            skillProgress.getSkill().getName(),
            skillProgress.getNote(),
            skillProgress.getLevel()
        ))
        .collect(Collectors.toList());
  }
}
