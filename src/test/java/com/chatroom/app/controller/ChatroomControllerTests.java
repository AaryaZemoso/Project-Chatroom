package com.chatroom.app.controller;


import com.chatroom.app.AppApplication;
import com.chatroom.app.convertor.ChatroomConvertor;
import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.entity.User;
import com.chatroom.app.exception.chatroom.ChatroomNotFoundException;
import com.chatroom.app.service.chatroom.ChatroomService;
import com.chatroom.app.service.chatroom.ChatroomServiceImpl;
import com.chatroom.app.service.user.UserService;
import com.chatroom.app.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "aarya@gmail.com", password = "aarya", authorities = {"ROLE_USER"})
class ChatroomControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatroomConvertor chatroomConvertor;

    @MockBean
    private ChatroomServiceImpl chatroomService;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void getChatroomList() throws Exception{

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/chatroom"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void getChatroomById() throws Exception{

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/chatroom/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getChatroomByIdThrowsException() throws Exception{

        doThrow(ChatroomNotFoundException.class).when(chatroomService).findById(999);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/chatroom/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void postChatroom() throws Exception{

        when(userService.findByEmail(anyString())).thenReturn(new User());
        doNothing().when(chatroomService).save(any(Chatroom.class));

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/chatroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"userId\":1, \"name\": \"nob\", \"description\":\"nob\"}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void putChatroom() throws Exception{

        when(userService.findByEmail(anyString())).thenReturn(new User());
        doNothing().when(chatroomService).save(any(Chatroom.class));

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/chatroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"userId\":1, \"name\": \"nob\", \"description\":\"nob\"}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteChatroom() throws Exception{

        doNothing().when(chatroomService).deleteById(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/chatroom/1").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteChatroomThrowsException() throws Exception{

        doThrow(ChatroomNotFoundException.class).when(chatroomService).deleteById(999);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/chatroom/999").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
