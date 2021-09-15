package com.chatroom.app.dto.chatroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatroomRequestDTO {
    private int id;
    private int userId;
    private String name;
    private String description;
}
