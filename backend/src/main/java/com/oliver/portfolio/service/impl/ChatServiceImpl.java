package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.model.ChatRoom;
import com.oliver.portfolio.model.Message;
import com.oliver.portfolio.repository.ChatRoomRepository;
import com.oliver.portfolio.repository.MessageRepository;
import com.oliver.portfolio.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
  private ChatRoomRepository chatRoomRepository;
  private MessageRepository messageRepository;
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public ChatServiceImpl(ChatRoomRepository chatRoomRepository, MessageRepository messageRepository) {
    this.chatRoomRepository = chatRoomRepository;
    this.messageRepository = messageRepository;
  }
  
  @Override
  public ChatRoom getOrCreate(String code) {
    LOGGER.info("Getting or creating chat room by code {}", code);
    return chatRoomRepository.findByCode(code)
        .orElseGet(() -> chatRoomRepository.save(new ChatRoom(code)));
  }
  
  @Override
  public Message save(String sender, String content, ChatRoom chatRoom) {
    LOGGER.info("Saving chat room {}", chatRoom);
    return messageRepository.save(new Message(sender, content, chatRoom));
  }
  
  @Override
  public List<Message> getMessages(ChatRoom chatRoom) {
    LOGGER.info("Getting messages from chat room {}", chatRoom);
    return messageRepository.findByRoomOrderByTimestampAsc(chatRoom);
  }
  
}
