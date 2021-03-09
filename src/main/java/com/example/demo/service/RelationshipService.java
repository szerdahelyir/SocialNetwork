package com.example.demo.service;

import com.example.demo.models.Relationship;
import com.example.demo.models.User;
import com.example.demo.repository.RelationshipRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {
    private final RelationshipRepository relationshipRepository;
    private final UserRepository userRepository;

    @Autowired
    public RelationshipService(RelationshipRepository relationshipRepository,
                               UserRepository userRepository) {
        this.relationshipRepository=relationshipRepository;
        this.userRepository = userRepository;
    }

    public Relationship addFriend(Long userId){
        boolean exists = userRepository.existsById(userId);
        if(!exists){
            throw new IllegalStateException(
                    "user with id " + userId + " does not exist"
            );
        }
        else if(AuthenticationUtil.getAuthenticatedUserId()==userId){
            throw new IllegalStateException(
                    "user can't add himself"
            );
        }
        User user1 = userRepository.findUserById(AuthenticationUtil.getAuthenticatedUserId());
        User user2 = userRepository.findUserById(userId);
        Relationship relationship=new Relationship(user1,user2,0,user1);
        return relationshipRepository.save(relationship);
    }
}
