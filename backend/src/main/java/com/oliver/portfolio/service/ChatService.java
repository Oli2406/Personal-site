package com.oliver.portfolio.service;

import com.oliver.portfolio.endpoint.dto.MessageSearchDto;
import com.oliver.portfolio.exception.ValidationException;
import com.oliver.portfolio.model.ChatRoom;
import com.oliver.portfolio.model.Message;
import com.oliver.portfolio.model.User;

import java.util.List;

public interface ChatService {
  ChatRoom getOrCreate(String code);
  
  Message save(String sender, String content, ChatRoom chatRoom) throws ValidationException;
  
  boolean isUserInRoom(String roomCode, String username);
  
  void addUserToRoom(User user, ChatRoom room);
  
  void removeUserFromRoom(User user, ChatRoom room);
  
  List<String> getJoinedRooms(String username);
  
  List<Message> getMessageAfterJoin(ChatRoom room, String username);
  
  MessageSearchDto searchMessages(Long roomId, String query, String username);
}
