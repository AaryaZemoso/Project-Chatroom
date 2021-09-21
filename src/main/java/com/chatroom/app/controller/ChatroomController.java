package com.chatroom.app.controller;

import com.chatroom.app.convertor.ChatroomConvertor;
import com.chatroom.app.dto.ResponseDTO;
import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.entity.User;
import com.chatroom.app.service.chatroom.ChatroomService;
import com.chatroom.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatroomController {

    @Autowired
    private ChatroomConvertor chatroomConvertor;

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private UserService userService;

    @GetMapping("/chatroom")
    public List<ChatroomResponseDTO> getAllChatroom(){
        return chatroomService.findAll();
    }

    @GetMapping("/chatroom/{id}")
    public ChatroomResponseDTO getChatroomById(@PathVariable("id") int id){
        Chatroom chatroom = chatroomService.findById(id);
        return chatroomConvertor.getResponse(chatroom);
    }

    @PostMapping("/chatroom")
    public ResponseDTO save(@RequestBody ChatroomRequestDTO chatroom){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User u = userService.findByEmail(securityContext.getAuthentication().getName());

        chatroom.setId(0);
        chatroom.setUserId(u.getId());

        chatroomService.save(chatroom);
        return new ResponseDTO("Added new chatroom");
    }

    @PutMapping("/chatroom")
    public ResponseDTO update(@RequestBody ChatroomRequestDTO chatroom){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User u = userService.findByEmail(securityContext.getAuthentication().getName());

        chatroom.setUserId(u.getId());

        chatroomService.save(chatroom);
        return new ResponseDTO("Updated chatroom");
    }

    @DeleteMapping("/chatroom/{id}")
    public ResponseDTO delete(@PathVariable int id){
        chatroomService.deleteById(id);
        return new ResponseDTO("Deleted chatroom with id " + id);
    }
}
