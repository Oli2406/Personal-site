package com.oliver.portfolio.repository;

import com.oliver.portfolio.model.ChatRoom;
import com.oliver.portfolio.model.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
  Optional<ChatRoomMember> findByRoom_CodeAndUser_Username(String roomCode, String username);
  
  List<ChatRoomMember> findAllByRoom_Code(String roomCode);
  
  List<ChatRoomMember> findAllByUser_Username(String username);
  
  void deleteByRoom_CodeAndUser_Username(String roomCode, String username);
  
  @Query("""
    SELECT m FROM ChatRoomMember m
    JOIN FETCH m.user
    WHERE m.room = :room
    """)
  List<ChatRoomMember> findAllByRoom(@Param("room") ChatRoom room);
}