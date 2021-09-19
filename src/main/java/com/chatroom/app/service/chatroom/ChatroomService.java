package com.chatroom.app.service.chatroom;

import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.entity.Chatroom;

import java.util.List;

public interface ChatroomService {
    Chatroom findById(int theId);
    void save(Chatroom chatroom);
    void deleteById(int theId);

    void save(ChatroomRequestDTO chatroom);
    List<ChatroomResponseDTO> findAll();
    List<ChatroomResponseDTO> findAllByUserId(int userid);
    boolean checkIfChatroomBelongsToUser(int chatroomId, int userId);
}
