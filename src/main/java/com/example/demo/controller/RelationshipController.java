package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relationships")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RelationshipController {
    @Autowired
    RelationshipService relationshipService;

    @PostMapping("/add/{userId}")
    public void addFriend(@PathVariable("userId") Long id) {
        this.relationshipService.addFriend(id);
    }

    @PutMapping("/accept/{userId}")
    public void acceptFriendRequest(@PathVariable("userId") Long id) {
        this.relationshipService.acceptFriendRequest(id);
    }

    @GetMapping("/friends")
    public Page<UserDTO> getFriends(@RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.relationshipService.getFriends(page,size);
    }
}
