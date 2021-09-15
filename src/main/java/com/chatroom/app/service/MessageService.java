package com.chatroom.app.service;

import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.entity.User;


public interface MessageService {
    void sendMessage(int userId, int chatroomId, String message);
}

