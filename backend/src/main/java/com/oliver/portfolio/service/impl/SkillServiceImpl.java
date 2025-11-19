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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        .map(skill -> {
            SkillDetailDto toSave = new SkillDetailDto(skill, skill.getUser());
            List<SkillProgress> progresses = skillProgressRepository.findAllByUser_IdAndSkill_Id(skill.getUser().getId(), skill.getId());
            int streak = calculateStreak(progresses);
            toSave.setSkillStreakDays(streak);
            return toSave;
        })
        .collect(Collectors.toList());
  }
  
  @Override
  public List<SkillDetailDto> getAllSkillsById(User user) {
    LOGGER.info("Getting all skills by user {}", user);
    return skillRepository.findByUserId(user.getId()).stream()
        .map(skill -> {
          SkillDetailDto toSave = new SkillDetailDto(skill, skill.getUser());
          List<SkillProgress> progresses = skillProgressRepository.findAllByUser_IdAndSkill_Id(skill.getUser().getId(), skill.getId());
          int streak = calculateStreak(progresses);
          toSave.setSkillStreakDays(streak);
          return toSave;
        })
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
          skillDetailDto.getProgress()
      );
      skillToSave.addProgress(progress);
      Skill saved = skillRepository.save(skillToSave);
      return new SkillDetailDto(saved, user);
      
    } else {
      LOGGER.info("Creating skill {}", skillDetailDto);
      skillToSave = new Skill(
          skillDetailDto.getName(),
          skillDetailDto.getDescription(),
          0,
          user,
          skillDetailDto.getProgress()
      );
      
      SkillProgress progressToSave = new SkillProgress(
          skillToSave,
          user,
          skillDetailDto.getDescription(),
          0
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
  
  private int calculateStreak(List<SkillProgress> skills) {
    if (skills.size() < 2) return 0;
    
    int streak = 0;
    
    for (int i = 0; i < skills.size() - 1; i++) {
      LocalDateTime beforeDate = skills.get(i).getTimestamp().toLocalDate().atStartOfDay();
      LocalDateTime afterDate  = skills.get(i + 1).getTimestamp().toLocalDate().atStartOfDay();
      
      long daysBetween = ChronoUnit.DAYS.between(beforeDate, afterDate);
      long positive = Math.abs(daysBetween);
      
      if (positive == 1) {
        streak++;
      } else if (positive > 1) {
        streak = 0;
      }
    }
    return streak;
  }
}
