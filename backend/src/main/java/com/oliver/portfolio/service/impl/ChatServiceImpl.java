package com.oliver.portfolio.service.impl;

import com.oliver.portfolio.endpoint.dto.ChatRoomStatsDto;
import com.oliver.portfolio.endpoint.dto.MessageAllChatRoomsSearchDto;
import com.oliver.portfolio.endpoint.dto.MessageSearchDto;
import com.oliver.portfolio.exception.ValidationException;
import com.oliver.portfolio.model.ChatRoom;
import com.oliver.portfolio.model.ChatRoomMember;
import com.oliver.portfolio.model.Message;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.ChatRoomMemberRepository;
import com.oliver.portfolio.repository.ChatRoomRepository;
import com.oliver.portfolio.repository.MessageRepository;
import com.oliver.portfolio.service.ChatService;
import com.oliver.portfolio.service.validator.MessageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.function.support.RouterFunctionMapping;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
  private final RouterFunctionMapping routerFunctionMapping;
  private final HandlerMapping resourceHandlerMapping;
  private ChatRoomRepository chatRoomRepository;
  private MessageRepository messageRepository;
  private ChatRoomMemberRepository chatRoomMemberRepository;
  private MessageValidator messageValidator;
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public ChatServiceImpl(ChatRoomRepository chatRoomRepository,
                         MessageRepository messageRepository,
                         ChatRoomMemberRepository chatRoomMemberRepository,
                         MessageValidator messageValidator, RouterFunctionMapping routerFunctionMapping, @Qualifier("resourceHandlerMapping") HandlerMapping resourceHandlerMapping) {
    this.chatRoomRepository = chatRoomRepository;
    this.messageRepository = messageRepository;
    this.chatRoomMemberRepository = chatRoomMemberRepository;
    this.messageValidator = messageValidator;
    this.routerFunctionMapping = routerFunctionMapping;
    this.resourceHandlerMapping = resourceHandlerMapping;
  }
  
  @Override
  public ChatRoom getOrCreate(String code) {
    LOGGER.info("Getting or creating chat room by code {}", code);
    return chatRoomRepository.findByCode(code)
        .orElseGet(() -> chatRoomRepository.save(new ChatRoom(code)));
  }
  
  @Override
  public Message save(String sender, String content, ChatRoom chatRoom) throws ValidationException {
    LOGGER.info("Saving chat room {}", chatRoom);
    messageValidator.validateMessage(new Message(sender, content, chatRoom));
    return messageRepository.save(new Message(sender, content, chatRoom));
  }
  
  @Override
  public boolean isUserInRoom(String roomCode, String username) {
    LOGGER.info("Checking if user {} is a in room {}", username, roomCode);
    LOGGER.info("Checking if user {} is in room {}", username, roomCode);
    return chatRoomMemberRepository.findByRoom_CodeAndUser_Username(roomCode, username).isPresent();
  }
  
  @Override
  @Transactional
  public void addUserToRoom(User user, ChatRoom room) {
    LOGGER.info("Adding user {} to room {}", user, room);
    chatRoomMemberRepository.findByRoom_CodeAndUser_Username(room.getCode(), user.getUsername())
        .orElseGet(() -> chatRoomMemberRepository.save(
            new ChatRoomMember(room,
                               user,
                               Instant.now(),
                               ChatRoomMember.MembershipStatus.ACTIVE)
            )
        );
  }
  
  @Override
  @Transactional
  public void removeUserFromRoom(User user, ChatRoom room) {
    LOGGER.info("Removing user {} from room {}", user.getUsername(), room);
    chatRoomMemberRepository.deleteByRoom_CodeAndUser_Username(room.getCode(), user.getUsername());
  }
  
  @Override
  public List<String> getJoinedRooms(String username) {
    LOGGER.info("Getting joined rooms for {}", username);
    List<String> rooms = chatRoomMemberRepository.findAllByUser_Username(username)
        .stream()
        .map(chatRoomMember -> chatRoomMember.getRoom().getCode())
        .toList();
    return rooms;
  }
  
  @Override
  public List<Message> getMessageAfterJoin(ChatRoom room, String username) {
    LOGGER.info("Getting messages after joining room {}", room);
    return messageRepository.findMessagesAfterUserJoined(room, username);
  }
  
  @Override
  public MessageSearchDto searchMessages(Long roomId, String query, String username) {
    LOGGER.info("Searching messages in room {} with query {} for user {}", roomId, query, username);
    
    ChatRoom chatRoom = chatRoomRepository.findById(roomId)
        .orElseThrow(() -> new IllegalArgumentException("Chat room not found"));
    
    List<Message> messages = messageRepository.findMessagesByRoomAndContentAfterUserJoined(chatRoom, query, username);
    
    return new MessageSearchDto(roomId, query, messages);
  }
  
  @Override
  public List<MessageAllChatRoomsSearchDto> searchMessagesAcrossJoinedRooms(String query, String username) {
    LOGGER.info("Searching messages across all joined rooms with query {} for user {}", query, username);
    List<Message> messages = messageRepository.findMessagesByContentAcrossJoinedRoomsAfterUserJoined(query, username);
    
    List<MessageAllChatRoomsSearchDto> toReturn = new ArrayList<>();
    
    for (Message message : messages) {
      toReturn.add(new MessageAllChatRoomsSearchDto(
          message.getRoom().getId(),
          message.getRoom().getCode(),
          query,
          message
      ));
    }
    return toReturn;
  }
  
  @Override
  public ChatRoomStatsDto getChatRoomStats(Long roomId) {
    int memberCount = chatRoomMemberRepository.countChatRoomMembersByRoomId(roomId);
    int messageCount = messageRepository.countMessagesByRoomId(roomId);
    String mostActiveUser = messageRepository.findMostActiveUserInRoom(roomId);
    int messagesFromMostActiveUser = messageRepository.countMessagesBySenderAndRoomId(mostActiveUser, roomId);
    List<Object[]> mostActiveDayHeatMapData = messageRepository.countMessagesByRoomIdGroupByDayOfWeek(roomId);
    
    return new ChatRoomStatsDto(
        roomId,
        memberCount,
        messageCount,
        mostActiveUser,
        messagesFromMostActiveUser,
        mostActiveDayHeatMapData
    );
  }
}

