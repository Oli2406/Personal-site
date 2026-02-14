package com.oliver.portfolio.datagen;

import com.oliver.portfolio.model.ChatRoom;
import com.oliver.portfolio.model.ChatRoomMember;
import com.oliver.portfolio.model.Message;
import com.oliver.portfolio.model.User;
import com.oliver.portfolio.repository.ChatRoomMemberRepository;
import com.oliver.portfolio.repository.ChatRoomRepository;
import com.oliver.portfolio.repository.MessageRepository;
import com.oliver.portfolio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
@Profile("dev")
@Order(2)
public class ChatRoomDataGenerator implements CommandLineRunner {
  
  private final ChatRoomMemberRepository chatRoomMemberRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  
  public ChatRoomDataGenerator(ChatRoomMemberRepository chatRoomMemberRepository,
                               ChatRoomRepository chatRoomRepository,
                               MessageRepository messageRepository,
                               UserRepository userRepository) {
    this.chatRoomMemberRepository = chatRoomMemberRepository;
    this.chatRoomRepository = chatRoomRepository;
    this.messageRepository = messageRepository;
    this.userRepository = userRepository;
  }
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ChatRoomDataGenerator.class);
  
  @Override
  public void run(String... args) throws Exception {
    LOGGER.info("Running ChatRoomDataGenerator...");
    
    long chatRoomCount = chatRoomRepository.count();
    long messageCount = messageRepository.count();
    
    if(chatRoomCount > 0 || messageCount > 0) {
      LOGGER.info("ChatRoomDataGenerator skipped, data already present.");
      return;
    }
    generateChatRooms();
    generateMessagesAndChatRoomMembers();
  }
  
  private void generateChatRooms() {
    List<String> chatRooms = Arrays.asList(
        "ChillZone",
        "TechTalks",
        "GamingHub",
        "MusicLounge",
        "BookNook",
        "CoffeeCorner",
        "StudyBuddy",
        "CodeCrafters",
        "MovieMania",
        "RandomChat"
    );
    chatRooms.forEach(name -> chatRoomRepository.save(new ChatRoom(name)));
    LOGGER.info("Generated {} chat rooms", chatRooms.size());
  }
  
  private static final List<String> SAMPLE_MESSAGES = Arrays.asList(
      "Hey everyone!",
      "What's up?",
      "Anyone here?",
      "This is a cool room",
      "How's it going?",
      "Just joined, hi!",
      "What are we talking about?",
      "Interesting topic",
      "I agree with that",
      "Not sure about that one",
      "Can someone explain?",
      "That makes sense",
      "Good point!",
      "lol",
      "haha nice",
      "brb",
      "I'm back",
      "Anyone want to chat?",
      "This is fun",
      "See you later!"
  );

  private void generateMessagesAndChatRoomMembers() {
    List<ChatRoom> rooms = chatRoomRepository.findAll();
    List<User> users = userRepository.findAll();

    Random random = new Random();

    rooms.forEach(room -> {
      int groupSize = random.nextInt(2, 5);
      Set<User> members = new HashSet<>();
      
      IntStream.range(0, groupSize)
          .mapToObj(i -> users.get(random.nextInt(users.size())))
          .filter(members::add)
          .forEach(user -> {
            chatRoomMemberRepository.save(
                new ChatRoomMember(room, user, Instant.now().minusSeconds(3600), ChatRoomMember.MembershipStatus.ACTIVE)
            );
          });
      
      List<User> memberList = members.stream().toList();
      int totalMessages = random.nextInt(50, 150);
      
      for (int i = 0; i < totalMessages; i++) {
        User sender = memberList.get(random.nextInt(memberList.size()));
        String content = SAMPLE_MESSAGES.get(random.nextInt(SAMPLE_MESSAGES.size()));
        Message msg = new Message(sender.getUsername(), content, room);
        int daysAgo = random.nextInt(30);
        int hoursAgo = random.nextInt(24);
        int minutesAgo = random.nextInt(60);
        long totalSecondsAgo = (daysAgo * 86400L) + (hoursAgo * 3600L) + (minutesAgo * 60L);
        msg.setTimestamp(Instant.now().minusSeconds(totalSecondsAgo));
        messageRepository.save(msg);
      }
    });

    LOGGER.info("Generated {} messages", messageRepository.count());
  }
}
