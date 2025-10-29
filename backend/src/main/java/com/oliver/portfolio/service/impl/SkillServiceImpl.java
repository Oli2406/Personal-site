package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.repository.SkillRepository;
import com.oliver.portfolio.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {
  private final SkillRepository skillRepository;
  
  public SkillServiceImpl(SkillRepository skillRepository) {
    this.skillRepository = skillRepository;
  }
  
  @Override
  public List<SkillDetailDto> getAllSkills() {
    List<Skill> skills = skillRepository.findAll();
    List<SkillDetailDto> toReturn = new ArrayList<>();
    for(Skill skill : skills){
      toReturn.add(new SkillDetailDto(skill));
    }
    return toReturn;
  }
  
  @Override
  public SkillDetailDto createSkill(SkillDetailDto skillDetailDto) {
    Skill skillToSave = new Skill(
        skillDetailDto.getName(),
        skillDetailDto.getDescription(),
        skillDetailDto.getProgress()
    );
    skillRepository.save(skillToSave);
    return new SkillDetailDto(skillToSave);
  }
  
  @Override
  public void deleteSkill(Long id) {
    this.skillRepository.deleteById(id);
  }
}
