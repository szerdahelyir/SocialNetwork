package com.example.demo.mapper;

import com.example.demo.dto.ChatDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.models.ChatMessage;
import org.mapstruct.Mapping;

public interface ChatMapper {
    @Mapping(target = "id", source = "chat.id")
    ChatDTO toChatDTO(ChatMessage chatMessage, UserDTO sender, UserDTO receiver);
}
