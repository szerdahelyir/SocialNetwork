package com.example.demo.mapper;

import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.UpdateUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.models.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "relationship", source = "relationship")
    @Mapping(target = "profilePicture", source = "profilePicture")
    UserDTO toUserDTO(User user, Integer relationship, ImageDTO profilePicture);

    List<UserDTO> toUserDTOs(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(UpdateUserDTO dto, @MappingTarget User user);

}
