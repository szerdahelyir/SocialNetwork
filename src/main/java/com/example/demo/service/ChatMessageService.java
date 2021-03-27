package com.example.demo.service;

import com.example.demo.dto.ChatMessageDTO;
import com.example.demo.dto.PostResponseDTO;
import com.example.demo.mapper.ChatMessageMapper;
import com.example.demo.mapper.ImageMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Chat;
import com.example.demo.models.ChatMessage;
import com.example.demo.models.MessageStatus;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ImageMapper imageMapper;

    public long countNewMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessageDTO> findChatMessages(Long senderId, Long recipientId) {
        Chat chat = chatRepository.findChat(senderId < recipientId ? senderId : recipientId,
                senderId > recipientId ? senderId : recipientId);

        List<ChatMessage> messages=chatMessageRepository.findMessagesByChatId(chat.getId());

        if(messages.size() > 0) {
            messages.stream()
            .map(message->{
                message.setStatus(MessageStatus.DELIVERED);
                return chatMessageRepository.save(message);});
        }
        List<ChatMessageDTO> responseDTOS = messages
                .stream()
                .map(message -> chatMessageMapper.toChatMessageDTO
                        (message,
                                userMapper.toUserDTO(message.getSender(),2, imageMapper.toImageDTO(message.getSender().getProfilePicture())),
                                userMapper.toUserDTO(message.getReceiver(),2, imageMapper.toImageDTO(message.getReceiver().getProfilePicture()))
                        )
                )
                .collect(Collectors.toList());
        return responseDTOS;
    }

    public ChatMessage findById(Long id) {
        return chatMessageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new IllegalStateException("can't find message (" + id + ")"));
    }

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

}
