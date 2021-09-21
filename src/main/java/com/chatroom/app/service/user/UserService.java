package com.chatroom.app.service.user;

import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> findAll();
    User findById(int id);
    void update(User user);
    void save(User user);
    void deleteById(int id);
    User findByEmail(String email);
}
