package com.oliver.portfolio.endpoint;

import com.oliver.portfolio.endpoint.dto.UserDetailDto;
import com.oliver.portfolio.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(AdminEndpoint.BASE_PATH)
public class AdminEndpoint {
  
  public static final String BASE_PATH = "/api/admin";
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public AdminService adminService;
  
  @GetMapping
  @Secured("ROLE_ADMIN")
  public ResponseEntity<List<UserDetailDto>> getAllUsers() {
    LOGGER.info("getAllUsers");
    return ResponseEntity.ok(adminService.getAllUsers());
  }
}
