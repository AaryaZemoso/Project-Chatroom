package com.chatroom.app.service;

import com.chatroom.app.convertor.ChatroomConvertor;
import com.chatroom.app.dao.chatroom.ChatroomRepository;
import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.exception.chatroom.ChatroomNotFoundException;
import com.chatroom.app.service.chatroom.ChatroomServiceImpl;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@WithMockUser(username = "aarya@gmail.com", password = "aarya", authorities = {"ROLE_USER"})
class ChatroomTests {

    @InjectMocks
    private ChatroomServiceImpl chatroomService;

    @Mock
    private ChatroomRepository chatroomRepository;

    @Mock
    private ChatroomConvertor chatroomConvertor;

    @Test
    void getAllEmployees(){

        List<Chatroom> listOfChatroom = new ArrayList<>();

        listOfChatroom.add(new Chatroom(1, "General", "no gen", null));
        listOfChatroom.add(new Chatroom(2, "Maths", "Geeks", null));
        listOfChatroom.add(new Chatroom(3, "Science", "Enjoys", null));

        when(chatroomRepository.findAll()).thenReturn(listOfChatroom);

        // Test
        List<ChatroomResponseDTO> chatroomList = chatroomService.findAll();

        assertEquals(3, chatroomList.size());
        verify(chatroomRepository, times(1)).findAll();
    }

    @Test
    void getChatroomById(){

        // when(chatroomRepository.findById(1)).thenReturn(java.util.Optional.of(new Chatroom(1, "General", "For everyone", null)));
        doReturn(java.util.Optional.of(new Chatroom(1, "General", "For everyone", null))).when(chatroomRepository).findById(1);

        Chatroom chatroom = chatroomService.findById(1);

        assertEquals("General", chatroom.getName());
        assertEquals("For everyone", chatroom.getDesc());
        assertEquals(1, chatroom.getId());

    }

    @Test
    void findAllById(){

        when(chatroomRepository.findAllById(Arrays.asList(1))).thenReturn(Arrays.asList(new Chatroom()));

        List<ChatroomResponseDTO> list = chatroomService.findAllByUserId(1);
        assertTrue(!list.isEmpty());
        assertEquals(1, list.size());

        when(chatroomRepository.findAllById(Arrays.asList(1))).thenReturn(Arrays.asList());

        list = chatroomService.findAllByUserId(1);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void saveChatroomRequestDTO(){

        when(chatroomConvertor.getChatroom(new ChatroomRequestDTO())).thenReturn(new Chatroom());
        when(chatroomRepository.save(any(Chatroom.class))).thenReturn(new Chatroom());

        chatroomService.save(new ChatroomRequestDTO());
        verify(chatroomRepository, times(1)).save(any(Chatroom.class));
    }

    @Test
    void saveChatroom(){

        when(chatroomRepository.save(any(Chatroom.class))).thenReturn(new Chatroom());

        Chatroom chatroom = new Chatroom(1, "General", "For everyone", null);
        chatroomService.save(chatroom);
        verify(chatroomRepository, times(1)).save(chatroom);
    }

    @Test
    void deleteChatroom(){

        when(chatroomRepository.findById(20)).thenReturn(Optional.of(new Chatroom()));
        doNothing().when(chatroomRepository).deleteById(20);

        try {
            chatroomService.deleteById(20);
            verify(chatroomRepository, times(1)).deleteById(20);
        } catch (Exception e){
            fail();
        }
    }

    @Test
    void throwErrorOnDeletingNonExistingChatroom(){
        try{
            chatroomService.deleteById(9999);
            fail("Deleted Id with 9999");
        }

        catch (ChatroomNotFoundException ce){
            verify(chatroomRepository, times(1)).findById(9999);
        }
    }

    @Test
    void throwErrorOnFindingNonExistingChatroom(){
        try{
            chatroomService.findById(9999);
            fail();
        }catch (ChatroomNotFoundException ce){
            verify(chatroomRepository, times(1)).findById(9999);
        }
    }
}
