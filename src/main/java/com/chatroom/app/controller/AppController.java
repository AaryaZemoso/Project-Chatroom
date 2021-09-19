package com.chatroom.app.controller;

import com.chatroom.app.convertor.ChatroomConvertor;
import com.chatroom.app.dao.authorities.AuthoritiesDAO;
import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.Authorities;
import com.chatroom.app.entity.Chatroom;
import com.chatroom.app.entity.Roles;
import com.chatroom.app.entity.User;
import com.chatroom.app.service.chatroom.ChatroomService;
import com.chatroom.app.service.security.SecurityService;
import com.chatroom.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class AppController {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private ChatroomConvertor chatroomConvertor;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesDAO authoritiesDAO;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ChatroomController chatroomController;

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        SecurityContext securityContext = SecurityContextHolder.getContext();

        User u = userService.findByEmail(securityContext.getAuthentication().getName());
        List<String> authoritiesList = authoritiesDAO.findByUsername(u.getEmail()).stream().map(authorities -> authorities.getAuthority()).collect(Collectors.toList());
        List<UserResponseDTO> userResponseDTOList = userService.findAll();
        List<ChatroomResponseDTO> chatroomList = chatroomService.findAll();

        model.addAttribute("user", u);
        model.addAttribute("authoritiesList", authoritiesList);
        model.addAttribute("userList", userResponseDTOList);
        model.addAttribute("chatroomList", chatroomList);

        if(!authoritiesList.contains("ROLE_ADMIN"))
            model.addAttribute("userChatroomList", chatroomService.findAllByUserId(u.getId()));
        else
            model.addAttribute("userChatroomList", chatroomList);
        return "dashboard";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        model.addAttribute("signUpUser", new User());
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("signUpUser") User user) throws Exception{

        user.setId(0);
        user.setEnabled(1);

        userService.save(user);

        return "redirect:/login";
    }

    @GetMapping("/chat/{id}")
    public String chatroom(@PathVariable("id") int id, HttpServletResponse response, Model model){

        User u = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<String> authoritiesList = authoritiesDAO.findByUsername(u.getEmail()).stream().map(authorities -> authorities.getAuthority()).collect(Collectors.toList());
        ChatroomResponseDTO chatroom = chatroomConvertor.getResponse(chatroomService.findById(id));
        List<UserResponseDTO> userResponseDTOList = userService.findAll();

        model.addAttribute("user", u);
        model.addAttribute("authoritiesList", authoritiesList);
        model.addAttribute("chatroom", chatroom);
        model.addAttribute("userList", userResponseDTOList);


        return "chatroom";
    }

    @PostMapping("/addChatroom")
    public String addChatroom(@RequestParam("chatroomName") String chatroomName, @RequestParam("chatroomDescription") String chatroomDescription){

        User u = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Chatroom chatroom = new Chatroom();

        chatroom.setId(0);
        chatroom.setUser(u);
        chatroom.setName(chatroomName);
        chatroom.setDesc(chatroomDescription);

        chatroomService.save(chatroom);

        return "redirect:/dashboard";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password){

        User u = new User();

        u.setId(0);
        u.setEnabled(1);

        u.setPassword(password);
        u.setName(username);
        u.setEmail(email);

        userService.save(u);

        return "redirect:/dashboard";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteById(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/deleteChatroom/{id}")
    public String deleteChatroom(@PathVariable("id") int id){

        SecurityContext securityContext = SecurityContextHolder.getContext();
        User u = userService.findByEmail(securityContext.getAuthentication().getName());
        List<String> authoritiesList = authoritiesDAO.findByUsername(u.getEmail()).stream().map(authorities -> authorities.getAuthority()).collect(Collectors.toList());

        if(!authoritiesList.contains("ROLE_ADMIN"))
        {
            if(chatroomService.checkIfChatroomBelongsToUser(id, u.getId()));
                chatroomService.deleteById(id);
        }

        else
            chatroomService.deleteById(id);

        return "redirect:/dashboard";
    }

}
