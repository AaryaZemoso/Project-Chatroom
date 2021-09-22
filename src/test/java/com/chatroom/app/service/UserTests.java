package com.chatroom.app.service;

import com.chatroom.app.convertor.UserConvertor;
import com.chatroom.app.dao.authorities.AuthoritiesDAOImpl;
import com.chatroom.app.dao.user.UserRepository;
import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.Authorities;
import com.chatroom.app.entity.User;
import com.chatroom.app.exception.user.UserNotFoundException;
import com.chatroom.app.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "aarya@gmail.com", password = "aarya", authorities = {"ROLE_USER"})
class UserTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserConvertor userConvertor;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    AuthoritiesDAOImpl authoritiesDAO;

    @Test
    void findAllUsers(){

        List<User> users = new ArrayList<>();
        users.add(new User(1, "name1", "email1", "pass1", 1));
        users.add(new User(1, "name1", "email1", "pass1", 1));
        users.add(new User(1, "name1", "email1", "pass1", 1));

        when(userRepository.findAll()).thenReturn(users);

        List<UserResponseDTO> userList = userService.findAll();

        assertEquals(userList, users.stream().map(userConvertor::getResponse).collect(Collectors.toList()));
    }

    @Test
    void findById(){

        User u = new User(1, "name1", "email1", "pass1", 1);
        when(userRepository.findById(1)).thenReturn(Optional.of(u));

        User user = userService.findById(1);
        verify(userRepository, times(1)).findById(1);

        assertEquals(user.getId(), u.getId());
        assertEquals(user.getEmail(), u.getEmail());
        assertEquals(user.getName(), u.getName());
        assertEquals(user.getPassword(), u.getPassword());
        assertEquals(user.getEnabled(), u.getEnabled());
    }

    @Test
    void update(){

        when(userRepository.save(new User())).thenReturn(new User());

        userService.update(new User());
        verify(userRepository, times(1)).save(new User());
    }

    @Test
    void save(){
        User u = new User(1, "name1", "email1", "pass1", 1);

        userService.save(u);
        verify(userRepository, times(1)).save(u);
        verify(authoritiesDAO, times(1)).save(any(Authorities.class));
    }

    @Test
    void findByEmailTest(){

        when(userRepository.findByEmail(anyString())).thenReturn(new User());

        userService.findByEmail(anyString());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void deleteById(){

        when(userRepository.findById(10)).thenReturn(Optional.of(new User()));
        doNothing().when(authoritiesDAO).delete(anyString());
        doNothing().when(userRepository).deleteById(10);

        userService.deleteById(10);

        verify(userRepository, times(1)).deleteById(10);
    }

    @Test
    void throwErrorDeletingNonExistingUser(){
        try{
            userService.deleteById(9999);
            fail();
        } catch (UserNotFoundException ue){
            verify(userRepository, times(1)).findById(9999);
        }
    }

    @Test
    void throwErrorFindingNonExistingUser(){
        try{
            userService.findById(9999);
            fail();
        } catch (UserNotFoundException ue){
            verify(userRepository, times(1)).findById(9999);
        }
    }
}
