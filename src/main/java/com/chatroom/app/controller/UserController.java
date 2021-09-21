package com.chatroom.app.controller;

import com.chatroom.app.convertor.UserConvertor;
import com.chatroom.app.dto.ResponseDTO;
import com.chatroom.app.dto.user.UserDTO;
import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.User;
import com.chatroom.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConvertor userConvertor;

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserResponseDTO getById(@PathVariable int id){
        User u = userService.findById(id);
        return userConvertor.getResponse(u);
    }

    @PostMapping("/users")
    public ResponseDTO save(@RequestBody UserDTO user){
        user.setId(0);
        user.setEnabled(1);
        User newUser = userConvertor.getUser(user);
        userService.save(newUser);
        return new ResponseDTO("Added User");
    }

    @PutMapping("/users")
    public ResponseDTO update(@RequestBody UserDTO user){
        User updatedUser = userConvertor.getUser(user);
        userService.update(updatedUser);
        return new ResponseDTO("Updated User");
    }

    @DeleteMapping("/users/{id}")
    public ResponseDTO delete(@PathVariable("id") int id){
        userService.deleteById(id);
        return new ResponseDTO("Deleted User with id " + id);
    }
}
