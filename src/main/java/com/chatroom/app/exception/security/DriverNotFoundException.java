package com.chatroom.app.exception.security;

public class DriverNotFoundException extends RuntimeException{
    public DriverNotFoundException(String message) {
        super(message);
    }

    public DriverNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverNotFoundException(Throwable cause) {
        super(cause);
    }
}
