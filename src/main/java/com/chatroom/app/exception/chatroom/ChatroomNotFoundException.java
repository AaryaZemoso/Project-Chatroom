package com.chatroom.app.exception.chatroom;

public class ChatroomNotFoundException extends RuntimeException{
    public ChatroomNotFoundException(String message) {
        super(message);
    }

    public ChatroomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatroomNotFoundException(Throwable cause) {
        super(cause);
    }
}
