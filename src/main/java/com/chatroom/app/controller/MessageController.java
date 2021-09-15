package com.chatroom.app.controller;

import com.chatroom.app.convertor.MessageConvertor;
import com.chatroom.app.dao.MessageDAO;
import com.chatroom.app.dto.message.MessageDTO;
import com.chatroom.app.dto.message.MessageResponseDTO;
import com.chatroom.app.entity.Message;
import com.chatroom.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;


    // TODO: Remove User ID from the Path and think of a way to get user id from the front end
    @MessageMapping("/chat/{chatroomId}")
    @SendTo("/message/{chatroomId}")
    public MessageDTO sendMessage(@DestinationVariable int chatroomId, MessageDTO message){
        messageService.sendMessage(message.getUserId(), chatroomId, message.getMessage());
        return message;
    }
}
