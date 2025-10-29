package com.oliver.portfolio.endpoint;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(SkillEndpoint.BASE_PATH)
public class SkillEndpoint {
  
  private final SkillService skillService;
  
  public SkillEndpoint(SkillService skillService) {
    this.skillService = skillService;
  }
  
  public static final String BASE_PATH = "api/skills";
  
  @GetMapping
  public ResponseEntity<List<SkillDetailDto>> getAllSkills() {
    List<SkillDetailDto> allSkills = this.skillService.getAllSkills();
    return ResponseEntity.ok(allSkills);
  }
  
  @PostMapping
  public ResponseEntity<SkillDetailDto> createSkill(@RequestBody SkillDetailDto skill) {
    SkillDetailDto createdSkill = skillService.createSkill(skill);
    return ResponseEntity.ok(createdSkill);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
    skillService.deleteSkill(id);
    return ResponseEntity.noContent().build();
  }
}
