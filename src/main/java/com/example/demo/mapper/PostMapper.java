package com.example.demo.mapper;

import com.example.demo.dto.PostResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "id", source = "post.id")
    @Mapping(target = "userDTO", source = "userDTO")
    @Mapping(target = "commentCount", source = "commentCount")
    PostResponseDTO toPostResponseDTO(Post post, UserDTO userDTO, Integer commentCount);

    List<PostResponseDTO> toPostResponseDTOs(List<PostResponseDTO> PostResponseDTOs);


}
