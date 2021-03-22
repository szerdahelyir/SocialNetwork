package com.example.demo.mapper;

import com.example.demo.dto.CommentResponseDTO;
import com.example.demo.dto.PostResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "userDTO", source = "userDTO")
    @Mapping(target = "comment", source = "comment.comment")
    CommentResponseDTO toCommentResponseDTO(Comment comment, UserDTO userDTO);

    List<CommentResponseDTO> toCommentResponseDTOs(List<PostResponseDTO> PostResponseDTOs);
}
