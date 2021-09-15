package com.chatroom.app.service;

import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> findAll();
    User findById(int id);
    void save(User user);
    void deleteById(int id);
}
