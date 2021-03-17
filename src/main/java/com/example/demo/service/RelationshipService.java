package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Relationship;
import com.example.demo.models.User;
import com.example.demo.repository.RelationshipRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelationshipService {
    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private User selectUserWithLowerId(User user, User otherUser) {
        return user.getId() < otherUser.getId() ? user : otherUser;
    }

    private User selectUserWithHigherId(User user, User otherUser) {
        return user.getId() > otherUser.getId() ? user : otherUser;
    }

    public Relationship addFriend(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException(
                    "user with id " + userId + " does not exist"
            );
        } else if (AuthenticationUtil.getAuthenticatedUserId().equals(userId)) {
            throw new IllegalStateException(
                    "user can't add himself"
            );
        }
        User user1 = userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        User user2 = userRepository.findUserById(userId);
        Relationship relationship = new Relationship(selectUserWithLowerId(user1, user2), selectUserWithHigherId(user1, user2), 0, user1);
        return relationshipRepository.save(relationship);
    }

    public void acceptFriendRequest(Long userId) {
        User actionUser = userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        Relationship relationship = relationshipRepository.findRelationship(userId < AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId(),
                userId > AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId());
        relationship.setStatus(1);
        relationship.setActionUser(actionUser);
        relationshipRepository.save(relationship);
    }

    public void declineFriendRequest(Long userId) {
        User actionUser = userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        Relationship relationship = relationshipRepository.findRelationship(userId < AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId(),
                userId > AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId());
        relationship.setStatus(2);
        relationship.setActionUser(actionUser);
        relationshipRepository.save(relationship);
    }

    public Page<UserDTO> getFriends(int page, int size) {
        Long currentId = AuthenticationUtil.getAuthenticatedUserId();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Relationship> relationships = relationshipRepository.findFriends(currentId, 1,pageRequest);
        List<User> friends = relationships.stream()
                .map(r -> r.getUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId()) ? r.getUser2() : r.getUser())
                .collect(Collectors.toList());
        List<UserDTO> UserDTOfriends = this.userMapper.toUserDTOs(friends);
        return new PageImpl<>(UserDTOfriends, pageRequest, relationships.getTotalElements());
    }

    public List<Long> getFriendsIds(){
        Long currentId = AuthenticationUtil.getAuthenticatedUserId();
        List<Relationship> relationships = relationshipRepository.findFriendRelationships(currentId, 1);
        List<Long> friendsIds = relationships.stream()
                .map(r -> r.getUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId()) ? r.getUser2().getId() : r.getUser().getId())
                .collect(Collectors.toList());
        return friendsIds;
    }

    public Integer relationshipWithUser(Long userId){
        if(relationshipRepository.findRelationship(userId < AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId(),
                userId > AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId())!=null) {
            Relationship relationship = relationshipRepository.findRelationship(userId < AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId(),
                    userId > AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId());
            // én küldtem neki requestet
            if (relationship.getActionUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId()) && relationship.getStatus().equals(0)) {
                return 0;
            }
            // ő küldött requestet nekem
            else if (relationship.getActionUser().getId().equals(userId) && relationship.getStatus().equals(0)) {
                return 1;
            }
            // barátok
            else if (relationship.getStatus().equals(1)) {
                return 2;
            }
        }
        // nem barátok
        return 3;
    }


}
