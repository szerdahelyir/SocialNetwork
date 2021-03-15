package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List<UserDTO> getUsers() {
        Long id = AuthenticationUtil.getAuthenticatedUserId();
        return userMapper.toUserDTOs(userService.getUsers(id));
    }

    @GetMapping(path = "{userId}")
    public UserDTO getUser(@PathVariable("userId") Long userId) {
        return userMapper.toUserDTO(userService.getUser(userId));
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
