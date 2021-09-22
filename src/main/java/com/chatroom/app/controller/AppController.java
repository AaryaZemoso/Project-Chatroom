package com.chatroom.app.controller;

import com.chatroom.app.convertor.ChatroomConvertor;
import com.chatroom.app.convertor.UserConvertor;
import com.chatroom.app.dao.authorities.AuthoritiesDAO;
import com.chatroom.app.dto.chatroom.ChatroomRequestDTO;
import com.chatroom.app.dto.chatroom.ChatroomResponseDTO;
import com.chatroom.app.dto.user.UserDTO;
import com.chatroom.app.dto.user.UserResponseDTO;
import com.chatroom.app.entity.Authorities;
import com.chatroom.app.entity.User;
import com.chatroom.app.service.chatroom.ChatroomService;
import com.chatroom.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AppController {

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private ChatroomConvertor chatroomConvertor;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConvertor userConvertor;

    @Autowired
    private AuthoritiesDAO authoritiesDAO;

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        model.addAttribute("signUpUser", new User());
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("signUpUser") UserDTO userDTO){

        userDTO.setId(0);
        userDTO.setEnabled(1);

        User newUser = userConvertor.getUser(userDTO);

        userService.save(newUser);

        return "redirect:/login";
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model){
        addCommonModelAttributes(model);
        return "dashboard";
    }


    @GetMapping("/chat/{id}")
    public String chatroom(@PathVariable("id") int id, Model model){
        addCommonModelAttributes(model);

        ChatroomResponseDTO chatroom = chatroomConvertor.getResponse(chatroomService.findById(id));
        model.addAttribute("chatroom", chatroom);

        return "chatroom";
    }


    @GetMapping("/updateChatroom/{id}")
    public String updateChatroom(@PathVariable("id") int id, Model model){
        addCommonModelAttributes(model);

        ChatroomRequestDTO chatroom = chatroomConvertor.getRequest(chatroomService.findById(id));
        model.addAttribute("update_chatroom", chatroom);

        return "update-chatroom";
    }

    // ---------------------------- Helper Functions ------------------------------------

    private void addCommonModelAttributes(Model model){

        SecurityContext securityContext = SecurityContextHolder.getContext();

        User u = userService.findByEmail(securityContext.getAuthentication().getName());
        List<String> authoritiesList = authoritiesDAO.findByUsername(u.getEmail()).stream().map(Authorities::getAuthority).collect(Collectors.toList());
        List<UserResponseDTO> userResponseDTOList = userService.findAll();
        List<ChatroomResponseDTO> chatroomList = chatroomService.findAll();

        model.addAttribute("chatroomList", chatroomList);
        model.addAttribute("user", u);
        model.addAttribute("authoritiesList", authoritiesList);
        model.addAttribute("userList", userResponseDTOList);

        if(!authoritiesList.contains("ROLE_ADMIN"))
            model.addAttribute("userChatroomList", chatroomService.findAllByUserId(u.getId()));
        else
            model.addAttribute("userChatroomList", chatroomList);
    }
}
