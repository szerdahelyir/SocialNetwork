package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOs(List<User> users);

}
