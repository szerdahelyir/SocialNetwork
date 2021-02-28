package com.example.demo.user;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/v1/users")
public class UserManagementController {

    private static final List<User> USERS= Arrays.asList(
            new User(1, "James Bond"),
            new User( 2, "Maria Jones"),
            new User(3,"Anna Smith")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<User> getAllUsers(){
        System.out.println("get all students");
        return USERS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public void registerNewUser(@RequestBody User user){
        System.out.println("register new user:");
        System.out.println(user);
    }

    @DeleteMapping(path="{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable("userId") Integer userId){
        System.out.println("delete:");
        System.out.println(userId);
    }

    @PutMapping(path = "{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void updateUser(@PathVariable("userId")Integer userId, @RequestBody User user){
        System.out.println("update users:");
        System.out.println(String.format("%s %s",userId,user));
    }
}
