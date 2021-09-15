package com.chatroom.app.convertor;

import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.User;

public class UserConvertor {

    public static UserResponseDTO getResponse(User user){

        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());

        return responseDTO;
    }
}
