package com.example.demo.mapper;

import com.example.demo.dto.ChatDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.models.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    @Mapping(target = "id", source = "chat.id")
    @Mapping(target = "receiver", source = "receiver")
    ChatDTO toChatDTO(Chat chat, UserDTO receiver);
}
