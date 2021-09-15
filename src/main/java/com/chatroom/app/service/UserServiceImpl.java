package com.chatroom.app.service;

import com.chatroom.app.convertor.UserConvertor;
import com.chatroom.app.dao.UserRepository;
import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.User;
import com.chatroom.app.exception.user.UserNotFoundException;
import com.chatroom.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserConvertor::getResponse)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(int id) {

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent())
            throw new UserNotFoundException("User with " + id + " not found");

        return user.get();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {

        if(this.findById(id) != null)
            userRepository.deleteById(id);
        else
            throw new UserNotFoundException("User with id " + id + " not found");
    }
}
