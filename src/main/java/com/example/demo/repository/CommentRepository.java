package com.example.demo.repository;

import com.example.demo.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN FETCH c.post JOIN FETCH c.user WHERE c.post.id = :postId ORDER BY c.creationDate ASC")
    List<Comment> findByPostId(@Param("postId")Long postId);
}
