package com.chatroom.app.dto.message;

import lombok.Data;

@Data
public class MessageResponseDTO {

    private int senderId;
    private String senderName;
    private String message;
}
