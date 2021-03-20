package com.example.demo.controller;

import com.example.demo.dto.PostResponseDTO;
import com.example.demo.models.Post;
import com.example.demo.dto.PostDTO;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/myposts")
    public List<PostResponseDTO> getCurrentUserPosts() {
        return this.postService.getPostsOfCurrentUser();
    }

    @GetMapping("/friends")
    public List<PostResponseDTO> getFriendsPosts() {
        return this.postService.getPostsOfFriends();
    }

    @GetMapping("/{userId}")
    public List<PostResponseDTO> getPostsOfUser(@PathVariable(value = "userId") Long userId) {
        return this.postService.getPostsOfUser(userId);
    }

    @PostMapping()
    public void createPost(@RequestBody PostDTO postDTO){
        this.postService.createPost(postDTO);
    }
}
