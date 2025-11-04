package com.oliver.portfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "chat_room_members",
    uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "user_id"}))
@Getter @Setter @NoArgsConstructor
public class ChatRoomMember {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  private ChatRoom room;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  @Column(name = "joined_at", nullable = false)
  private java.time.Instant joinedAt = Instant.now();
  
  @Column(name = "last_seen")
  private java.time.Instant lastSeen;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private MembershipStatus status = MembershipStatus.ACTIVE;
  
  public enum MembershipStatus {
    ACTIVE, LEFT, BANNED
  }
  
  public ChatRoomMember(ChatRoom room,
                        User user,
                        Instant lastSeen,
                        MembershipStatus status) {
    this.room = room;
    this.user = user;
    this.lastSeen = lastSeen;
    this.status = status;
  }
}
