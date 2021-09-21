package com.chatroom.app.service.chatroom;

import com.chatroom.app.convertor.ChatroomConvertor;
import com.chatroom.app.dao.chatroom.ChatroomRepository;
import com.chatroom.app.dao.user.UserRepository;
import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.exception.chatroom.ChatroomNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatroomServiceImpl implements ChatroomService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private ChatroomConvertor chatroomConvertor;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ChatroomResponseDTO> findAll(){
        return chatroomRepository.findAll()
                .stream()
                .map(chatroomConvertor::getResponse)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public List<ChatroomResponseDTO> findAllByUserId(int userId) {

        Session session = entityManager.unwrap(Session.class);

        Query<Chatroom> query = session.createQuery("from Chatroom where user_id = :userId");
        query.setParameter("userId", userId);

        List<Chatroom> listOfChatrooms = query.getResultList();
        if(listOfChatrooms == null)
            return new ArrayList<>();

        return listOfChatrooms.stream().map(chatroomConvertor::getResponse).collect(Collectors.toList());

    }

    @Override
    public boolean checkIfChatroomBelongsToUser(int chatroomId, int userId) {

        Session session = entityManager.unwrap(Session.class);

        Query<Chatroom> query = session.createQuery("from Chatroom where id = :chatroomId and user_id = :userId");
        query.setParameter("chatroomId", chatroomId);
        query.setParameter("userId", userId);

        return !query.getResultList().isEmpty();
    }

    @Override
    public Chatroom findById(int id) {

        Optional<Chatroom> chatroom = chatroomRepository.findById(id);

        if(!chatroom.isPresent())
            throw new ChatroomNotFoundException("Chatroom with id " + id + " not found");

        return chatroom.get();
    }

    @Override
    public void save(Chatroom chatroom) {
        chatroomRepository.save(chatroom);
    }

    @Override
    public void save(ChatroomRequestDTO chatroom) {

        Chatroom newChatroom = chatroomConvertor.getChatroom(chatroom);
        chatroomRepository.save(newChatroom);
    }

    @Override
    public void deleteById(int id) {

        if(this.findById(id) == null)
            throw new ChatroomNotFoundException("Chatroom with id " + id + " not found");

        chatroomRepository.deleteById(id);
    }
}
