package com.example.demo.service;

import com.example.demo.dto.CommentResponseDTO;
import com.example.demo.dto.PostResponseDTO;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.ImageMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Comment;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageService imageService;

    public List<CommentResponseDTO> getCommentsOfPost(Long postId){
        List<Comment> comments = commentRepository.findByPostId(postId);

        List<CommentResponseDTO> responseDTOS = comments
                .stream()
                .map(c -> commentMapper.toCommentResponseDTO
                        (c,
                                userMapper.toUserDTO(c.getUser(), relationshipService.relationshipWithUser(c.getUser().getId()),imageMapper.toImageDTO(c.getUser().getProfilePicture(),imageService.decompressBytes(c.getUser().getProfilePicture().getPicByte())))
                        )
                )
                .collect(Collectors.toList());
        return responseDTOS;
    }

    public void createComment(Long postId, String content) {
        Post post=this.postRepository.findPostById(postId);
        User user=this.userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        Comment comment = new Comment(content,user,post);
        this.commentRepository.save(comment);
    }
}
