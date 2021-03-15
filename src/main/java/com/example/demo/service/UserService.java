package com.example.demo.service;


import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(Long userId) {
        return userRepository.findAllExceptUserWithId(userId);
    }


    public User getUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException(
                    "user with id " + userId + " does not exist"
            );
        }
        return userRepository.findUserById(userId);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException(
                    "user with id " + userId + " does not exist"
            );
        }
        userRepository.deleteById(userId);
    }
}
