package com.chatroom.app.controller;

import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.service.ChatroomService;
import com.chatroom.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")
public class ChatroomController {

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<ChatroomResponseDTO> getAllChatrooms(){
        return chatroomService.findAll();
    }

    @PostMapping("/save")
    public String saveOrUpdate(@RequestBody ChatroomRequestDTO chatroom){
        chatroomService.save(chatroom);
        return "Saved chatroom";
    }

    @GetMapping("/delete/{id}")
    public String deleteChatroom(@PathVariable int id){
        chatroomService.deleteById(id);
        return "Deleted id : " + id;
    }
}
