package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Relationship;
import com.example.demo.models.User;
import com.example.demo.repository.RelationshipRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<UserDTO> getFriends() {
        Long currentId = AuthenticationUtil.getAuthenticatedUserId();
        List<Relationship> relationships = relationshipRepository.findFriends(currentId, 1);
        List<User> friends = relationships.stream()
                .map(r -> r.getUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId()) ? r.getUser2() : r.getUser())
                .collect(Collectors.toList());
        return this.userMapper.toUserDTOs(friends);
    }

    public List<Long> getFriendsIds(){
        Long currentId = AuthenticationUtil.getAuthenticatedUserId();
        List<Relationship> relationships = relationshipRepository.findFriends(currentId, 1);
        List<Long> friendsIds = relationships.stream()
                .map(r -> r.getUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId()) ? r.getUser2().getId() : r.getUser().getId())
                .collect(Collectors.toList());
        return friendsIds;
    }


}
