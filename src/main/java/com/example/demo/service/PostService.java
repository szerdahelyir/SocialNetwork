package com.example.demo.service;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.dto.PostDTO;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationshipService relationshipService;

    public List<Post> getPostsOfCurrentUser(){
        Long id = AuthenticationUtil.getAuthenticatedUserId();
        return postRepository.findAllByUserId(id);
    }

    public List<Post> getPostsOfFriends(){
        List<Long> friendIds=this.relationshipService.getFriendsIds();
        return this.postRepository.findPostsByUserIds(friendIds);
    }

    public void createPost(PostDTO postDTO){
        User user=userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        Post post=new Post(postDTO.getContent(),user);
        postRepository.save(post);
    }

//    public void updatePost(PostDTO postDTO){
//        User user=userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
//        Post post=new Post(postDTO.getContent(),user);
//        postRepository.save(post);
//    }
}
