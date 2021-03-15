package com.example.demo.repository;

import com.example.demo.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.user.id = :userid")
    List<Post> findAllByUserId(@Param("userid") Long userid);

    @Query(value = "SELECT p FROM Post p JOIN FETCH p.user WHERE p.user.id IN :ids")
    List<Post> findPostsByUserIds(@Param("ids") Collection<Long> ids);

    Post findPostById(Long id);
}
