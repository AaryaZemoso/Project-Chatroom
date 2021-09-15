package com.chatroom.app.dao;

import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.entity.Message;
import com.chatroom.app.entity.User;
import com.chatroom.app.service.ChatroomService;
import com.chatroom.app.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Repository
public class MessageDAOImpl implements MessageDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void sendMessage(User user, Chatroom chatroom, String message) {

        Message newMessage = new Message();

        newMessage.setId(0);
        newMessage.setUser(user);
        newMessage.setMessage(message);
        newMessage.setChatroom(chatroom);
        newMessage.setTimestamp(LocalDateTime.now());

        entityManager.merge(newMessage);
    }
}
