package com.oliver.portfolio.endpoint;

import com.oliver.portfolio.endpoint.dto.SkillDetailDto;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.UserRepository;
import com.oliver.portfolio.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(SkillEndpoint.BASE_PATH)
public class SkillEndpoint {
  
  private final SkillService skillService;
  private final UserRepository userRepository;
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public SkillEndpoint(SkillService skillService, UserRepository userRepository) {
    this.skillService = skillService;
    this.userRepository = userRepository;
  }
  
  public static final String BASE_PATH = "/api/skills";
  
  @GetMapping
  public ResponseEntity<List<SkillDetailDto>> getAllSkills(Authentication authentication) {
    String username = authentication.getName();
    User user = userRepository.findByUsername(username);
    List<SkillDetailDto> allSkills = skillService.getAllSkillsById(user);
    LOGGER.info("GET {} - user {}", BASE_PATH, username);
    return ResponseEntity.ok(allSkills);
  }
  
  @PostMapping
  public ResponseEntity<SkillDetailDto> createSkill(@RequestBody SkillDetailDto skill, Authentication authentication) {
    String username = authentication.getName();
    User user = userRepository.findByUsername(username);
    
    SkillDetailDto createdSkill = skillService.createSkill(skill, user);
    LOGGER.info("POST {} - user {} - payload {}", BASE_PATH, username, skill);
    return ResponseEntity.ok(createdSkill);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
    skillService.deleteSkill(id);
    LOGGER.info("DELETE {} - Payload: {}", BASE_PATH, id);
    return ResponseEntity.noContent().build();
  }
}
