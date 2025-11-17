package com.oliver.portfolio.endpoint;

import com.oliver.portfolio.endpoint.dto.SkillProgressDto;
import com.oliver.portfolio.endpoint.dto.SkillUpdateDto;
import com.oliver.portfolio.model.Skill;
import com.oliver.portfolio.model.SkillProgress;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.SkillRepository;
import com.oliver.portfolio.repository.UserRepository;
import com.oliver.portfolio.service.SkillProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(SkillProgressEndpoint.BASE_PATH)
public class SkillProgressEndpoint {
  
  public static final String BASE_PATH = "/api/skillProgress";
  private final SkillRepository skillRepository;
  
  private UserRepository userRepository;
  private SkillProgressService skillProgressService;
  
  
  public SkillProgressEndpoint(UserRepository userRepository,
                               SkillProgressService skillProgressService, SkillRepository skillRepository) {
    this.userRepository = userRepository;
    this.skillProgressService = skillProgressService;
    this.skillRepository = skillRepository;
  }
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  @GetMapping("/{skillId}")
  public ResponseEntity<List<SkillProgressDto>> getSkillProgress(@PathVariable Long skillId, Authentication authentication) {
    String username = authentication.getName();
    User user = userRepository.findByUsername(username);
    Skill skill = skillRepository.findById(skillId).get();
    
    LOGGER.info("GET: " + BASE_PATH + "/{}", user.getId());
    return ResponseEntity.ok(skillProgressService.getAllUpdatesBySkill(user.getId(), skill.getId()));
  }
  
  @PostMapping
  public ResponseEntity<SkillProgressDto> createSkillProgress(
      @RequestBody SkillUpdateDto skill,
      Authentication authentication
  ) {
    String username = authentication.getName();
    User requester = userRepository.findByUsername(username);
    
    LOGGER.info("POST: user - {}, payload {}", username, skill);
    
    SkillProgress saved = skillProgressService.save(skill, requester);
    
    SkillProgressDto dto = new SkillProgressDto(
        saved.getId(),
        saved.getSkill().getName(),
        saved.getNote(),
        saved.getSessionTimeMinutes(),
        saved.getTimestamp()
    );
    
    return ResponseEntity.ok(dto);
  }
  
}
