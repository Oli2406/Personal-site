package com.oliver.portfolio.service;

import com.oliver.portfolio.model.ChatRoom;
import com.oliver.portfolio.model.Message;

import java.util.List;

public interface ChatService {
  ChatRoom getOrCreate(String code);
  
  Message save(String sender, String content, ChatRoom chatRoom);
  
  List<Message> getMessages(ChatRoom chatRoom);
}
