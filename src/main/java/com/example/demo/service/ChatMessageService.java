package com.example.demo.service;

import com.example.demo.dto.ChatDTO;
import com.example.demo.dto.ChatMessageDTO;
import com.example.demo.mapper.ChatMapper;
import com.example.demo.mapper.ChatMessageMapper;
import com.example.demo.mapper.ImageMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Chat;
import com.example.demo.models.ChatMessage;
import com.example.demo.models.MessageStatus;
import com.example.demo.models.User;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ImageService imageService;

    @Autowired
    private ChatMapper chatMapper;

    public long countNewMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessageDTO> findChatMessages(Long senderId, Long recipientId) {
        Chat chat = chatRepository.findChat(senderId < recipientId ? senderId : recipientId,
                senderId > recipientId ? senderId : recipientId);

        List<ChatMessage> messages = chatMessageRepository.findMessagesByChatId(chat.getId());

        if (messages.size() > 0) {
            messages.stream()
                    .map(message -> {
                        message.setStatus(MessageStatus.DELIVERED);
                        return chatMessageRepository.save(message);
                    });
        }
        List<ChatMessageDTO> responseDTOS = messages
                .stream()
                .map(message -> chatMessageMapper.toChatMessageDTO
                        (message,
                                userMapper.toUserDTO(message.getSender(), 2, message.getSender().getProfilePicture() == null ? null : imageMapper.toImageDTO(message.getSender().getProfilePicture(), imageService.decompressBytes(message.getSender().getProfilePicture().getPicByte()))),
                                userMapper.toUserDTO(message.getReceiver(), 2, message.getReceiver().getProfilePicture() == null ? null : imageMapper.toImageDTO(message.getReceiver().getProfilePicture(), imageService.decompressBytes(message.getReceiver().getProfilePicture().getPicByte())))
                        )
                )
                .collect(Collectors.toList());
        return responseDTOS;
    }

    public ChatMessageDTO findById(Long id) {
        ChatMessage msg = chatMessageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new IllegalStateException("can't find message (" + id + ")"));
        return chatMessageMapper.toChatMessageDTO(msg,
                userMapper.toUserDTO(msg.getSender(), 2, msg.getSender().getProfilePicture() == null ? null : imageMapper.toImageDTO(msg.getSender().getProfilePicture(), imageService.decompressBytes(msg.getSender().getProfilePicture().getPicByte()))),
                userMapper.toUserDTO(msg.getReceiver(), 2, msg.getReceiver().getProfilePicture() == null ? null : imageMapper.toImageDTO(msg.getReceiver().getProfilePicture(), imageService.decompressBytes(msg.getReceiver().getProfilePicture().getPicByte())))
        );
    }

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatDTO> findChats() {
        List<Chat> chats = chatRepository.findChats(AuthenticationUtil.getAuthenticatedUserId());

        List<ChatDTO> chatDTOS = chats
                .stream()
                .map(c -> {
                    User recipient = c.getUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId()) ? c.getUser2() : c.getUser();
                    return chatMapper.toChatDTO
                            (c,
                                    userMapper.toUserDTO(recipient, 2, recipient.getProfilePicture() == null ? null : imageMapper.toImageDTO(recipient.getProfilePicture(), imageService.decompressBytes(recipient.getProfilePicture().getPicByte()))));
                })
                .collect(Collectors.toList());
        return chatDTOS;
    }

}
