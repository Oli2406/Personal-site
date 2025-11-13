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
import java.util.Optional;
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
  public SkillDetailDto createOrUpdateSkill(SkillDetailDto skillDetailDto, User user) {
    Skill skillToSave;
    
    Optional<Skill> skillPresent = skillRepository.findById(skillDetailDto.getId());
    
    if(skillPresent.isPresent()) {
      LOGGER.info("Updating skill {}", skillDetailDto.getProgress());
      skillToSave = skillPresent.get();
      skillToSave.setTargetProgressMinutes(skillPresent.get().getTargetProgressMinutes());
      skillToSave.addTime(skillDetailDto.getProgress());
      int newProgress = skillToSave.computeProgressPercentage();
      skillToSave.setName(skillDetailDto.getName());
      skillToSave.setDescription(skillDetailDto.getDescription());
      skillToSave.setProgress(newProgress);
      
      SkillProgress progress = new SkillProgress(
          skillToSave,
          user,
          skillDetailDto.getDescription(),
          newProgress
      );
      skillToSave.addProgress(progress);
      Skill saved = skillRepository.save(skillToSave);
      return new SkillDetailDto(saved, user);
      
    } else {
      LOGGER.info("Creating skill {}", skillDetailDto);
      skillToSave = new Skill(
          skillDetailDto.getName(),
          skillDetailDto.getDescription(),
          skillDetailDto.getProgress(),
          user,
          3600
      );
      
      SkillProgress progressToSave = new SkillProgress(
          skillToSave,
          user,
          skillDetailDto.getDescription(),
          skillDetailDto.getProgress()
      );
      
      skillToSave.addProgress(progressToSave);
      Skill saved = skillRepository.save(skillToSave);
      skillProgressRepository.save(progressToSave);
      
      return new SkillDetailDto(saved, user);
    }
  }
  
  @Override
  public void deleteSkill(Long id) {
    LOGGER.info("Deleting skill {}", id);
    skillRepository.deleteById(id);
  }
}
