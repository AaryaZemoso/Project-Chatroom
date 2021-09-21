package com.chatroom.app.dto.chatroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomResponseDTO {
    private int chatroomId;
    private int ownerId;
    private String ownerName;
    private String chatroomName;
    private String description;
}
