package com.chatroom.app.convertor;

import com.chatroom.app.dao.authorities.AuthoritiesDAO;
import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConvertor {

    @Autowired
    private AuthoritiesDAO authoritiesDAO;

    public UserResponseDTO getResponse(User user){

        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(authoritiesDAO
                .findByUsername(user.getEmail())
                .stream()
                .map(authorities -> authorities.getAuthority())
                .collect(Collectors.toList())
        );

        return responseDTO;
    }

}
