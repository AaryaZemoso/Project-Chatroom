package com.chatroom.app.dao.message;

import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface MessageDAO {
    void sendMessage(User user, Chatroom chatroom, String message);
}
