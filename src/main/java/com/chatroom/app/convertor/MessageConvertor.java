package com.chatroom.app.convertor;

import com.chatroom.app.dto.message.MessageDTO;
import com.chatroom.app.dto.message.MessageResponseDTO;
import com.chatroom.app.entity.Message;
import com.chatroom.app.entity.User;
import com.chatroom.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConvertor {

    @Autowired
    private UserService userService;

    public MessageResponseDTO getResponse(Message message){

        MessageResponseDTO responseDTO = new MessageResponseDTO();

        responseDTO.setSenderId(message.getId());
        responseDTO.setSenderName(message.getUser().getName());
        responseDTO.setMessage(message.getMessageContent());

        return responseDTO;
    }

    public MessageResponseDTO getResponse(MessageDTO message){

        MessageResponseDTO responseDTO = new MessageResponseDTO();
        User u = userService.findById(message.getUserId());

        responseDTO.setSenderId(message.getUserId());
        responseDTO.setSenderName(u.getName());
        responseDTO.setMessage(message.getMessage());

        return responseDTO;
    }
}
