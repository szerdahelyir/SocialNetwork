package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.ImageMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Chat;
import com.example.demo.models.Relationship;
import com.example.demo.models.User;
import com.example.demo.repository.ChatRepository;
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

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageService imageService;

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
        User user1 = userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        User user2 = userRepository.findUserById(userId);
        Chat chat = new Chat(selectUserWithLowerId(user1, user2), selectUserWithHigherId(user1, user2));
        chatRepository.save(chat);
    }

    public void declineFriendRequest(Long userId) {
        User actionUser = userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        Relationship relationship = relationshipRepository.findRelationship(userId < AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId(),
                userId > AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId());
        relationshipRepository.delete(relationship);
    }

    public Page<UserDTO> getFriends(int page, int size) {
        Long currentId = AuthenticationUtil.getAuthenticatedUserId();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Relationship> relationships = relationshipRepository.findFriends(currentId, 1, pageRequest);
        List<UserDTO> friends = relationships.stream()
                .map(r -> {
                    User toreturn = r.getUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId()) ? r.getUser2() : r.getUser();

                    if (toreturn.getProfilePicture() == null) {
                        return userMapper.toUserDTO(toreturn, relationshipWithUser(toreturn.getId()), imageMapper.toImageDTO(toreturn.getProfilePicture(), null));
                    }
                    return userMapper.toUserDTO(toreturn, relationshipWithUser(toreturn.getId()), imageMapper.toImageDTO(toreturn.getProfilePicture(), imageService.decompressBytes(toreturn.getProfilePicture().getPicByte())));

                })
                .collect(Collectors.toList());
        return new PageImpl<>(friends, pageRequest, relationships.getTotalElements());
    }

    public List<UserDTO> getFriendRequests() {
        Long currentId = AuthenticationUtil.getAuthenticatedUserId();
        List<Relationship> relationships = relationshipRepository.findFriendFriendRequests(currentId, 0);
        List<UserDTO> friends = relationships.stream()
                .map(r -> {
                    User toreturn = r.getUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId()) ? r.getUser2() : r.getUser();

                    if (toreturn.getProfilePicture() == null) {
                        return userMapper.toUserDTO(toreturn, relationshipWithUser(toreturn.getId()), imageMapper.toImageDTO(toreturn.getProfilePicture(), null));
                    }
                    return userMapper.toUserDTO(toreturn, relationshipWithUser(toreturn.getId()), imageMapper.toImageDTO(toreturn.getProfilePicture(), imageService.decompressBytes(toreturn.getProfilePicture().getPicByte())));
                })
                .collect(Collectors.toList());
        return friends;
    }

    public List<Long> getFriendsIds() {
        Long currentId = AuthenticationUtil.getAuthenticatedUserId();
        List<Relationship> relationships = relationshipRepository.findFriendRelationships(currentId, 1);
        List<Long> friendsIds = relationships.stream()
                .map(r -> r.getUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId()) ? r.getUser2().getId() : r.getUser().getId())
                .collect(Collectors.toList());
        return friendsIds;
    }

    public Integer relationshipWithUser(Long userId) {
        if (relationshipRepository.findRelationship(userId < AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId(),
                userId > AuthenticationUtil.getAuthenticatedUserId() ? userId : AuthenticationUtil.getAuthenticatedUserId()) != null) {
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
