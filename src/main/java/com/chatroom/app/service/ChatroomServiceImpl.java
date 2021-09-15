package com.chatroom.app.service;

import com.chatroom.app.convertor.ChatroomConvertor;
import com.chatroom.app.dao.ChatroomRepository;
import com.chatroom.app.dao.UserRepository;
import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.exception.chatroom.ChatroomNotFoundException;
import com.chatroom.app.service.ChatroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ChatroomResponseDTO> findAll(){
        return chatroomRepository.findAll()
                .stream()
                .map(chatroomConvertor::getResponse)
                .collect(Collectors.toList());
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
