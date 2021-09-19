package com.chatroom.app.dto.message;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageDTO {
    private int userId;
    private String message;
}
