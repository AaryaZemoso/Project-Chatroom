package com.chatroom.app.service.security;

import com.chatroom.app.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

    User getLoggedInUser();
    void autoLogin(String username, String password, HttpServletRequest request);
}
