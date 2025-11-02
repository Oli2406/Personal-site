package com.oliver.portfolio.repository;

import com.oliver.portfolio.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  
  Optional<ChatRoom> findByCode(String code);
  
}
