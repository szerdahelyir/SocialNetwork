package com.example.demo.repository;

import com.example.demo.models.Relationship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    @Query("SELECT r FROM Relationship r WHERE r.user.id = :user1id AND r.user2.id = :user2id")
    Relationship findRelationship(@Param("user1id") Long user1id, @Param("user2id") Long user2id);

    @Query("SELECT r FROM Relationship r JOIN r.user JOIN r.user2 JOIN r.actionUser WHERE (r.user.id = :userid OR r.user2.id = :userid) AND r.status = :status")
    Page<Relationship> findFriends(@Param("userid") Long userid, @Param("status") Integer status, Pageable pageable);

    @Query("SELECT r FROM Relationship r JOIN FETCH r.user JOIN FETCH r.user2 JOIN FETCH r.actionUser WHERE (r.user.id = :userid OR r.user2.id = :userid) AND r.status = :status")
    List<Relationship> findFriendRelationships(@Param("userid") Long userid, @Param("status") Integer status);

    @Query("SELECT r FROM Relationship r JOIN FETCH r.user JOIN FETCH r.user2 JOIN FETCH r.actionUser WHERE (r.user.id = :userid OR r.user2.id = :userid) AND r.status = :status AND r.actionUser.id != :userid")
    List<Relationship> findFriendFriendRequests(@Param("userid") Long userid, @Param("status") Integer status);
}
