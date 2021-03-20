package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.dto.PostResponseDTO;
import com.example.demo.mapper.PostMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    public List<PostResponseDTO> getPostsOfCurrentUser() {
        Long id = AuthenticationUtil.getAuthenticatedUserId();
        List<Post> posts = postRepository.findAllByUserId(id);

        List<PostResponseDTO> responseDTOS = posts
                .stream()
                .map(p -> postMapper.toPostResponseDTO
                        (p,
                                userMapper.toUserDTO(p.getUser(), 4),
                                commentRepository.findByPostId(p.getId()).size()
                        )
                )
                .collect(Collectors.toList());
        return responseDTOS;
    }

    public List<PostResponseDTO> getPostsOfUser(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);

        List<PostResponseDTO> responseDTOS = posts
                .stream()
                .map(p -> postMapper.toPostResponseDTO
                        (p,
                                userMapper.toUserDTO(p.getUser(), 2),
                                commentRepository.findByPostId(p.getId()).size()
                        )
                )
                .collect(Collectors.toList());
        return responseDTOS;
    }

    public List<PostResponseDTO> getPostsOfFriends() {
        List<Long> friendIds =relationshipService.getFriendsIds();
        friendIds.add(AuthenticationUtil.getAuthenticatedUserId());
        List<Post> posts = postRepository.findPostsByUserIds(friendIds);

        List<PostResponseDTO> responseDTOS = posts
                .stream()
                .map(p -> postMapper.toPostResponseDTO
                        (p,
                                userMapper.toUserDTO(p.getUser(), 2),
                                commentRepository.findByPostId(p.getId()).size()
                        )
                )
                .collect(Collectors.toList());
        return responseDTOS;
    }

    public void createPost(PostDTO postDTO) {
        User user = userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        Post post = new Post(postDTO.getContent(), user);
        postRepository.save(post);
    }

//    public void updatePost(PostDTO postDTO){
//        User user=userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
//        Post post=new Post(postDTO.getContent(),user);
//        postRepository.save(post);
//    }
}
