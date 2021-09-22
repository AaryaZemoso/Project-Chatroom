package com.chatroom.app.controller;

import com.chatroom.app.AppApplication;
import com.chatroom.app.convertor.ChatroomConvertor;
import com.chatroom.app.convertor.UserConvertor;
import com.chatroom.app.dao.authorities.AuthoritiesDAOImpl;
import com.chatroom.app.dao.user.UserRepository;
import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.entity.Authorities;
import com.chatroom.app.entity.User;
import com.chatroom.app.service.chatroom.ChatroomServiceImpl;
import com.chatroom.app.service.user.UserService;
import com.chatroom.app.service.user.UserServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "aarya@gmail.com", password = "aarya", authorities = {"ROLE_USER"})
class AppControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    AuthoritiesDAOImpl authoritiesDAO;

    @MockBean
    ChatroomServiceImpl chatroomService;

    @MockBean
    ChatroomConvertor chatroomConvertor;

    @BeforeEach
    void init(){
        when(userService.findByEmail(anyString())).thenReturn(new User());
        when(chatroomConvertor.getRequest(any())).thenReturn(new ChatroomRequestDTO());
        when(chatroomConvertor.getResponse(any())).thenReturn(new ChatroomResponseDTO());
        when(authoritiesDAO.findByUsername(anyString())).thenReturn(Arrays.asList(new Authorities(1, "", "ROLE_ADMIN")));
    }

    @Test
    void checkAccessToRegister() throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ROLE_USER", "ROLE_ADMIN"})
    void checkAccessToDashboard() throws Exception{
        when(authoritiesDAO.findByUsername(anyString())).thenReturn(Arrays.asList(new Authorities(1, "", "ROLE_USER")));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/dashboard").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.model().attributeExists("userChatroomList"));
    }

    @Test
    void checkAccessToChatroom() throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/chatroom"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void checkAccessForUpdateChatroomPage() throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/updateChatroom/1").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void checkAccessLoginPage() throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void checkAccessChatPage() throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/chat/1").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void checkPostForRegister() throws Exception{

        doNothing().when(userService).save(any(User.class));

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/register")
                        .param("email", "aarya@gmail.com")
                        .param("password", "aarya")
                        .param("name", "aarya")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is(302))
        ;
    }

    @Test
    void checkIfUserAlreadyExists() throws Exception{

        doCallRealMethod().when(userService).save(any(User.class));

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/register")
                        .param("email", "aarya@gmail.com")
                        .param("password", "aarya")
                        .param("name", "aarya")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is(403))
        ;
    }
}
