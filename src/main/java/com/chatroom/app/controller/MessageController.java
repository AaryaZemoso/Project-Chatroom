package com.chatroom.app.controller;

import com.chatroom.app.convertor.MessageConvertor;
import com.chatroom.app.dto.message.MessageDTO;
import com.chatroom.app.dto.message.MessageResponseDTO;
import com.chatroom.app.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageConvertor messageConvertor;

    @MessageMapping("/chats/{chatroomId}")
    @SendTo("/message/{chatroomId}")
    public MessageResponseDTO sendMessage(@DestinationVariable int chatroomId, MessageDTO message){
        messageService.sendMessage(message.getUserId(), chatroomId, message.getMessage());
        return messageConvertor.getResponse(message);
    }
}
