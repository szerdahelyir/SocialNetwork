package com.example.demo.controller;

import com.example.demo.dto.MessageRequestDTO;
import com.example.demo.mapper.ImageMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Chat;
import com.example.demo.models.ChatMessage;
import com.example.demo.models.ChatNotification;
import com.example.demo.models.User;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ChatMessageService;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageService imageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageRequestDTO chatMessage) {
        Chat chat = chatRepository.findChat(chatMessage.getSenderId() < chatMessage.getRecipientId() ? chatMessage.getSenderId() : chatMessage.getRecipientId(),
                chatMessage.getSenderId() > chatMessage.getRecipientId() ? chatMessage.getSenderId() : chatMessage.getRecipientId());

        User sender=userRepository.findUserById(chatMessage.getSenderId());
        User recipient=userRepository.findUserById(chatMessage.getRecipientId());

        ChatMessage message=new ChatMessage(chatMessage.getMessage(),chat,sender,recipient);

        ChatMessage saved=chatMessageService.save(message);
        chat.setLastMessage(saved.getCreationDate());
        chatRepository.save(chat);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId().toString(),"/queue/messages",
                new ChatNotification(
                        userMapper.toUserDTO(saved.getSender(),2,
                                imageMapper.toImageDTO(saved.getSender().getProfilePicture(),
                                imageService.decompressBytes(saved.getSender().getProfilePicture().getPicByte()))),
                        saved.getId()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable Long senderId,
            @PathVariable Long recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable Long senderId,
                                                @PathVariable Long recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable Long id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }
}
