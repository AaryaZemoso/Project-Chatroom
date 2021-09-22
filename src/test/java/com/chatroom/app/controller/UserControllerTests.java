package com.chatroom.app.controller;

import com.chatroom.app.convertor.UserConvertor;
import com.chatroom.app.dto.user.UserDTO;
import com.chatroom.app.entity.User;
import com.chatroom.app.exception.user.UserNotFoundException;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "aarya@gmail.com", password = "aarya", authorities = {"ROLE_USER"})
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    UserConvertor userConvertor;

    @Test
    void getUserList() throws Exception{

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void getUserById() throws Exception{

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getUserByIdThrowsException() throws Exception{

        doThrow(UserNotFoundException.class).when(userService).findById(anyInt());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/9999")
                        .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void postUser() throws Exception{

        when(userConvertor.getUser(any(UserDTO.class))).thenReturn(new User());
        doNothing().when(userService).save(any(User.class));

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void putUser() throws Exception{

        when(userConvertor.getUser(any(UserDTO.class))).thenReturn(new User());
        doNothing().when(userService).save(any(User.class));

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteUser() throws Exception{

        doNothing().when(userService).deleteById(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/users/1").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteUserThrowsException() throws Exception{

        doThrow(UserNotFoundException.class).when(userService).deleteById(9999);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/users/9999").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}
