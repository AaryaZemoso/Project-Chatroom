package com.chatroom.app.service.security;

import com.chatroom.app.dao.authorities.AuthoritiesDAO;
import com.chatroom.app.entity.Authorities;
import com.chatroom.app.entity.User;
import com.chatroom.app.service.user.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthoritiesDAO authoritiesDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public User getLoggedInUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            String email = ((UserDetails)userDetails).getUsername();
            return userService.findByEmail(email);
        }

        return null;
    }

    @Override
    public void autoLogin(String username, String password, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken
                usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password,
                            userDetails.getAuthorities()
                            );

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));

        logger.info(userDetails.toString());
        logger.info(usernamePasswordAuthenticationToken.toString());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);

    }
}
