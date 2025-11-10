package com.oliver.portfolio.repository;

import com.oliver.portfolio.model.SkillProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillProgressRepository extends JpaRepository<SkillProgress, Long> {
  
  List<SkillProgress> findAllByUser_Id(Long userId);
  
}
