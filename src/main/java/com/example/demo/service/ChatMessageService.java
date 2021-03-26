package com.example.demo.service;

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

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRepository chatRepository;

    public long countNewMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId) {
        Chat chat = chatRepository.findChat(senderId < recipientId ? senderId : recipientId,
                senderId > recipientId ? senderId : recipientId);

        List<ChatMessage> messages=chatMessageRepository.findMessagesByChatId(chat.getId());

        if(messages.size() > 0) {
            messages.stream()
            .map(message->{
                message.setStatus(MessageStatus.DELIVERED);
                return chatMessageRepository.save(message);});
        }

        return messages;
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

}
