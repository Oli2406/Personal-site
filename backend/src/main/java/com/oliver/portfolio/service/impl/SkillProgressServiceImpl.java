package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.endpoint.dto.SkillProgressDto;
import com.oliver.portfolio.endpoint.dto.SkillUpdateDto;
import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.model.SkillProgress;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.SkillProgressRepository;
import com.oliver.portfolio.repository.SkillRepository;
import com.oliver.portfolio.service.SkillProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
  public SkillProgress save(SkillUpdateDto skill, User user) {
    LOGGER.info("Saving SkillProgress");
    
    Skill existingSkill = skillRepository.findByNameAndUser(skill.getName(), user);
    
    SkillProgress progress = new SkillProgress(
        existingSkill,
        user,
        skill.getDescription(),
        0
    );
    
    return skillProgressRepository.save(progress);
  }
  
  @Override
  public List<SkillProgressDto> getAllUpdatesBySkill(Long userId, int skillId) {
    LOGGER.info("Getting all SkillProgress updates for userId {} and skillId: {}", userId, skillId);
    List<SkillProgress> skills = skillProgressRepository.findAllByUser_IdAndSkill_Id(userId, skillId);
    int skillPracticeStreak = calculateStreak(skills);
    return skills.stream()
        .map(skillProgress -> {
          SkillProgressDto skillProgressDto = new SkillProgressDto(
              skillProgress.getId(),
              skillProgress.getSkill().getName(),
              skillProgress.getNote(),
              skillProgress.getSessionTimeMinutes(),
              skillProgress.getTimestamp()
          );
          skillProgressDto.setSkillStreakDays(skillPracticeStreak);
          return skillProgressDto;
        })
        .collect(Collectors.toList());
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
