package com.example.demo.service;


import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.ImageMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.User;
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
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageService imageService;

    public Page<UserDTO> getUsers(int page, int size) {
        Long id = AuthenticationUtil.getAuthenticatedUserId();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> pageResult = userRepository.findAll(id,pageRequest);
        List<UserDTO> users = pageResult
                .stream()
                .map(u->{
                    if(u.getProfilePicture()==null){
                        return userMapper.toUserDTO(u,relationshipService.relationshipWithUser(u.getId()),null);
                    }
                    return userMapper.toUserDTO(u,relationshipService.relationshipWithUser(u.getId()),imageMapper.toImageDTO(u.getProfilePicture(),imageService.decompressBytes(u.getProfilePicture().getPicByte())));
                })
                .collect(Collectors.toList());

        return new PageImpl<>(users, pageRequest, pageResult.getTotalElements());

    }


    public UserDTO getUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException(
                    "user with id " + userId + " does not exist"
            );
        }
        User u = userRepository.findUserById(userId);
        if(u.getProfilePicture()==null){
            return userMapper.toUserDTO(u,this.relationshipService.relationshipWithUser(userId),null);
        }
        return userMapper.toUserDTO(u,this.relationshipService.relationshipWithUser(userId),imageMapper.toImageDTO(u.getProfilePicture(),imageService.decompressBytes(u.getProfilePicture().getPicByte())));
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
