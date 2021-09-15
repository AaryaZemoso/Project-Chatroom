package com.chatroom.app.dto.chatroom;

import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.entity.User;
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

    public ChatroomResponseDTO(Chatroom c){
        this.chatroomId = c.getId();
        this.chatroomName = c.getName();
        this.description = c.getDesc();

        User tempUser = c.getUser();

        this.ownerId = tempUser.getId();
        this.ownerName = tempUser.getName();
    }
}
