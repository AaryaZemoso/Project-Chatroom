package com.chatroom.app.service.message;

public interface MessageService {
    void sendMessage(int userId, int chatroomId, String message);
}

