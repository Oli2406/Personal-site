package com.oliver.portfolio.endpoint.dto;

import java.util.List;

public class ChatRoomStatsDto {
  
  private Long roomId;
  private int memberCount;
  private int messageCount;
  private String mostActiveMember;
  private int mostActiveMemberMessageCount;
  private int[][] mostActiveDayHeatMap = new int[1][6];
  
  public ChatRoomStatsDto(
      Long roomId,
      int memberCount,
      int messageCount,
      String mostActiveMember,
      int mostActiveMemberMessageCount,
      List<Object[]> mostActiveDayHeatMapData) {
    this.roomId = roomId;
    this.memberCount = memberCount;
    this.messageCount = messageCount;
    this.mostActiveMember = mostActiveMember;
    this.mostActiveMemberMessageCount = mostActiveMemberMessageCount;
    for (Object[] row : mostActiveDayHeatMapData) {
      int dayOfWeek = (int) row[0];
      int hourOfDay = (int) row[1];
      int count = ((Number) row[2]).intValue();
      this.mostActiveDayHeatMap[dayOfWeek][hourOfDay] = count;
    }
  }
  
  public Long getRoomId() {
    return roomId;
  }
  
  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }
  
  public int getMessageCount() {
    return messageCount;
  }
  
  public void setMessageCount(int messageCount) {
    this.messageCount = messageCount;
  }
  
  public int getMemberCount() {
    return memberCount;
  }
  
  public void setMemberCount(int memberCount) {
    this.memberCount = memberCount;
  }
  
  public String getMostActiveMember() {
    return mostActiveMember;
  }
  
  public void setMostActiveMember(String mostActiveMember) {
    this.mostActiveMember = mostActiveMember;
  }
  
  public int getMostActiveMemberMessageCount() {
    return mostActiveMemberMessageCount;
  }
  
  public void setMostActiveMemberMessageCount(int mostActiveMemberMessageCount) {
    this.mostActiveMemberMessageCount = mostActiveMemberMessageCount;
  }
}
