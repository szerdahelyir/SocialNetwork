package com.example.demo.controller;

import com.example.demo.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> authenticateUser(@PathVariable("userId") Long id) {
        this.relationshipService.addFriend(id);

        return ResponseEntity.ok().build();
    }
}
