package com.example.demo.controller;

import com.example.demo.dto.UpdateUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.RelationshipService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RelationshipService relationshipService;

    @GetMapping
    public Page<UserDTO> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        return userService.getUsers(page, size);
    }

    @GetMapping(path = "/search")
    public Page<UserDTO> getSearchedUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "10") int size,
                                          @RequestParam(name = "name") String name) {
        return userService.getSearchedUsers(page, size, name);
    }

    @GetMapping(path = "{userId}")
    public UserDTO getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/edit/{userId}")
    public void updateUser(@RequestBody UpdateUserDTO updateUserDTO,
                           @PathVariable("userId") Long userId) {
        userService.updateUser(updateUserDTO, userId);
    }
}
