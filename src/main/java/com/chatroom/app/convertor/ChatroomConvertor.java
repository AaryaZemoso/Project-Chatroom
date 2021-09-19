package com.chatroom.app.convertor;

import com.chatroom.app.dao.user.UserRepository;
import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.entity.User;
import com.chatroom.app.exception.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChatroomConvertor {

    @Autowired
    private UserRepository userRepository;

    public ChatroomResponseDTO getResponse(Chatroom chatroom){

        ChatroomResponseDTO responseDTO = new ChatroomResponseDTO();

        responseDTO.setChatroomId(chatroom.getId());
        responseDTO.setChatroomName(chatroom.getName());
        responseDTO.setDescription(chatroom.getDesc());

        responseDTO.setOwnerId(chatroom.getUser().getId());
        responseDTO.setOwnerName(chatroom.getUser().getName());

        return responseDTO;
    }

    public Chatroom getChatroom(ChatroomRequestDTO c){

        Chatroom newChatroom = new Chatroom();

        newChatroom.setId(c.getId());
        newChatroom.setName(c.getName());
        newChatroom.setDesc(c.getDescription());

        Optional<User> user = userRepository.findById(c.getUserId());

        if(!user.isPresent())
            throw new UserNotFoundException("User with id " + c.getUserId() + " not found");

        newChatroom.setUser(user.get());

        return newChatroom;
    }

}
