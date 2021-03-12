package com.example.demo.controller;

import com.example.demo.models.Relationship;
import com.example.demo.models.User;
import com.example.demo.service.RelationshipService;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/relationships")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RelationshipController {
    @Autowired
    RelationshipService relationshipService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addFriend(@PathVariable("userId") Long id) {
        this.relationshipService.addFriend(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/accept/{userId}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable("userId") Long id) {
        this.relationshipService.acceptFriendRequest(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/friends")
    public List<User> getFriends(){
        List<User> friends=this.relationshipService.getFriends().stream()
                .map(r->r.getUser().getId().equals(AuthenticationUtil.getAuthenticatedUserId())?r.getUser2():r.getUser())
                .collect(Collectors.toList());
        return friends;
    }
}
