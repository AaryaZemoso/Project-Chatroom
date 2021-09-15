package com.chatroom.app.convertor;

import com.chatroom.app.dto.message.MessageResponseDTO;
import com.chatroom.app.entity.Message;

public class MessageConvertor {

    public static MessageResponseDTO getResponse(Message message){

        MessageResponseDTO responseDTO = new MessageResponseDTO();

        responseDTO.setSenderName(message.getUser().getName());
        responseDTO.setMessage(message.getMessage());
        responseDTO.setTimestamp(message.getTimestamp());

        return responseDTO;
    }
}
