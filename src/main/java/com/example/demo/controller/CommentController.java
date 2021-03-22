package com.example.demo.controller;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.CommentResponseDTO;
import com.example.demo.models.Comment;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/{postId}")
    public void createComment(@PathVariable(value = "postId") Long postId,
                                 @RequestBody CommentDTO comment) {
        this.commentService.createComment(postId,comment.getComment());
    }

    @GetMapping("/{postId}")
    public List<CommentResponseDTO> getCommentsOfPost(@PathVariable(value = "postId") Long postId){
        return this.commentService.getCommentsOfPost(postId);
    }
}
