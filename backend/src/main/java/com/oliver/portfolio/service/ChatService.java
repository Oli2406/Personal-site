package com.oliver.portfolio.service;

import com.oliver.portfolio.model.ChatRoom;
import com.oliver.portfolio.model.Message;
import com.oliver.portfolio.model.User;

import java.util.List;

public interface ChatService {
  ChatRoom getOrCreate(String code);
  
  Message save(String sender, String content, ChatRoom chatRoom);
  
  List<Message> getMessages(ChatRoom chatRoom);
  
  boolean isUserInRoom(String roomCode, String username);
  
  void addUserToRoom(User user, ChatRoom room);
  
  void removeUserFromRoom(User user, ChatRoom room);
}
