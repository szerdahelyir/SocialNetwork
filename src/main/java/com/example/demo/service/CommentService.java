package com.example.demo.service;

import com.example.demo.models.Comment;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    public List<Comment> getCommentsOfPost(Long postId){
        return this.commentRepository.findByPostId(postId);
    }

    public void createComment(Long postId, String content) {
        Post post=this.postRepository.findPostById(postId);
        User user=this.userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        Comment comment = new Comment(content,user,post);
        this.commentRepository.save(comment);
    }
}
