package com.chatroom.app.controller;

import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.User;
import com.chatroom.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<UserResponseDTO> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/save")
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        userService.deleteById(id);
    }
}
