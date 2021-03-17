package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target="relationship", source = "relationship")
    UserDTO toUserDTO(User user, Integer relationship);

    List<UserDTO> toUserDTOs(List<User> users);

}
