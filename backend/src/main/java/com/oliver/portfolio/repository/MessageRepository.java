package com.oliver.portfolio.repository;

import com.oliver.portfolio.model.ChatRoom;
import com.oliver.portfolio.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  
  List<Message> findByRoomOrderByTimestampAsc(ChatRoom room);
  
  @Query("""
    SELECT m
    FROM Message m
    WHERE m.room = :room
        AND m.timestamp > (
        SELECT cm.joinedAt
        FROM ChatRoomMember cm
        WHERE cm.room = :room AND cm.user.username = :username
        )
        ORDER BY m.timestamp ASC
    """)
  List<Message> findMessagesAfterUserJoined(
      @Param("room") ChatRoom room,
      @Param("username") String username
  );
  
  @Query("""
    SELECT m
    FROM Message m
    JOIN FETCH m.room
    WHERE m.room = :room
    AND LOWER(m.content) LIKE LOWER(CONCAT('%', :content, '%'))
    AND m.timestamp > (
        SELECT cm.joinedAt
        FROM ChatRoomMember cm
        WHERE cm.room = :room AND cm.user.username = :username
    )
    ORDER BY m.timestamp ASC
    """)
  List<Message> findMessagesByRoomAndContentAfterUserJoined(
      ChatRoom room,
      String content,
      String username
  );
  
  @Query("""
    SELECT m
    FROM Message m
    JOIN FETCH m.room
    WHERE LOWER(m.content) LIKE LOWER(CONCAT('%', :content, '%'))
    AND m.timestamp > (
        SELECT cm.joinedAt
        FROM ChatRoomMember cm
        WHERE cm.room = m.room AND cm.user.username = :username
    )
    AND m.room.id IN (
        SELECT cm.room.id
        FROM ChatRoomMember cm
        WHERE cm.user.username = :username
    )
    """)
  List<Message> findMessagesByContentAcrossJoinedRoomsAfterUserJoined(
      @Param("content") String content,
      @Param("username") String username
  );
  
  int countMessagesByRoomId(Long roomId);
}

