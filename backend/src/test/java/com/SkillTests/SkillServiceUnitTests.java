package com.SkillTests;

import com.oliver.portfolio.BackendApplication;
import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.enums.Role;
import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.model.SkillProgress;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.SkillProgressRepository;
import com.oliver.portfolio.repository.SkillRepository;
import com.oliver.portfolio.service.impl.SkillServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = BackendApplication.class)
@Transactional
class SkillServiceUnitTests {
  
  @Mock
  SkillRepository skillRepository;
  
  @Mock
  SkillProgressRepository skillProgressRepository;
  
  @InjectMocks
  SkillServiceImpl skillService;
  
  private User createTestUser() {
    User user = new User();
    user.setId(1L);
    user.setUsername("username");
    user.setPassword("password");
    user.setRole(Role.USER);
    return user;
  }
  
  private List<Skill> createTestSkillsList() {
    List<Skill> skills = new ArrayList<>();
    User user = createTestUser();
    for (int i = 0; i < 9; i++) {
      Skill skill = new Skill();
      skill.setId(i);
      skill.setName("skill" + i);
      skill.setDescription("skillDescription" + i);
      skill.setProgress(50);
      skill.setTargetProgressMinutes(100);
      skill.setUser(user);
      skills.add(skill);
    }
    return skills;
  }
  
  private Skill createTestSkill() {
    User user = createTestUser();
    Skill skill = new Skill();
    skill.setId(1);
    skill.setName("skill");
    skill.setDescription("skillDescription");
    skill.setProgress(0);
    skill.setTargetProgressMinutes(100);
    skill.setUser(user);
    return skill;
  }
  
  @Test
  void createsSkill_whenSkillDoesNotExist() {
    User user = createTestUser();
    
    SkillDetailDto dto = new SkillDetailDto();
    dto.setId(1);
    dto.setName("Guitar");
    dto.setDescription("Practice chords");
    dto.setProgress(50);
    dto.setTargetMinutes(1000);
    dto.setSkillStreakDays(0);
    
    when(skillRepository.findById(1L)).thenReturn(Optional.empty());
    when(skillRepository.save(any(Skill.class))).thenAnswer(inv -> inv.getArgument(0));
    
    SkillDetailDto result = skillService.createOrUpdateSkill(dto, user);
    
    ArgumentCaptor<Skill> skillCaptor = ArgumentCaptor.forClass(Skill.class);
    verify(skillRepository).save(skillCaptor.capture());
    verify(skillProgressRepository).save(any());
    verify(skillRepository).findById(1L);
    
    Skill savedSkill = skillCaptor.getValue();
    assertThat(savedSkill.getName()).isEqualTo("Guitar");
    assertThat(savedSkill.getDescription()).isEqualTo("Practice chords");
    assertThat(savedSkill.getUser()).isEqualTo(user);
    
    assertThat(savedSkill.getProgress()).isEqualTo(0);
    
    assertThat(result.getName()).isEqualTo("Guitar");
    assertThat(result.getDescription()).isEqualTo("Practice chords");
  }
  
  @Test
  void getAllSkillsReturnsListOfAllSkills() {
    List<Skill> skills = createTestSkillsList();
    
    when(skillRepository.findAll()).thenReturn(skills);
    when(skillProgressRepository.findAllByUser_IdAndSkill_Id(anyLong(), anyInt()))
        .thenReturn(Collections.emptyList());
    
    List<SkillDetailDto> result = skillService.getAllSkills();
    
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(9);
    assertThat(result.get(0).getName()).isEqualTo("skill0");
    
    verify(skillRepository).findAll();
    verify(skillProgressRepository, atLeastOnce())
        .findAllByUser_IdAndSkill_Id(anyLong(), anyInt());
  }
  
  @Test
  void getAllSkillsSetsStreakDaysToThree_whenThreeConsecutiveDays() {
    Skill skill = createTestSkill();
    
    when(skillRepository.findAll()).thenReturn(List.of(skill));
    
    SkillProgress p1 = new SkillProgress(skill, skill.getUser(), "note", 25);
    p1.setTimestamp(Timestamp.valueOf(LocalDateTime.of(2026, 2, 10, 12, 0)).toLocalDateTime());
    
    SkillProgress p2 = new SkillProgress(skill, skill.getUser(), "note", 15);
    p2.setTimestamp(Timestamp.valueOf(LocalDateTime.of(2026, 2, 11, 12, 0)).toLocalDateTime());
    
    SkillProgress p3 = new SkillProgress(skill, skill.getUser(), "note", 10);
    p3.setTimestamp(Timestamp.valueOf(LocalDateTime.of(2026, 2, 12, 12, 0)).toLocalDateTime());
    
    when(skillProgressRepository.findAllByUser_IdAndSkill_Id(anyLong(), anyInt()))
        .thenReturn(List.of(p1, p2, p3));
    
    List<SkillDetailDto> result = skillService.getAllSkills();
    
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getSkillStreakDays()).isEqualTo(3);
    
    verify(skillRepository).findAll();
    verify(skillProgressRepository).findAllByUser_IdAndSkill_Id(anyLong(), anyInt());
  }
}