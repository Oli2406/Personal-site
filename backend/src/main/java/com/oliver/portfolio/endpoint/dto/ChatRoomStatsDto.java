package com.oliver.portfolio.endpoint.dto;

public class ChatRoomStatsDto {
  
  private Long roomId;
  private int memberCount;
  private int messageCount;
  
  public ChatRoomStatsDto(Long roomId, int memberCount, int messageCount) {
    this.roomId = roomId;
    this.memberCount = memberCount;
    this.messageCount = messageCount;
  }
}
