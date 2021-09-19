package com.chatroom.app.service.message;

import com.chatroom.app.dao.chatroom.ChatroomRepository;
import com.chatroom.app.dao.message.MessageDAO;
import com.chatroom.app.dao.user.UserRepository;
import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.entity.User;
import com.chatroom.app.exception.chatroom.ChatroomNotFoundException;
import com.chatroom.app.exception.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Override
    public void sendMessage(int userId, int chatroomId, String message) {

        Optional<User> tempUser = userRepository.findById(userId);
        if(!tempUser.isPresent())
            throw new UserNotFoundException("User with id - " + userId + " not found");

        Optional<Chatroom> tempChatroom = chatroomRepository.findById(chatroomId);
        if(!tempChatroom.isPresent())
            throw new ChatroomNotFoundException("Chatroom with id - " + chatroomId + " not found");

        User user = tempUser.get();
        Chatroom chatroom = tempChatroom.get();

        if(user.getId() == chatroom.getUser().getId())
            user = chatroom.getUser();

        messageDAO.sendMessage(user, chatroom, message);
    }
}
