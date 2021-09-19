package com.chatroom.app.service.user;

import com.chatroom.app.dao.authorities.AuthoritiesDAO;
import com.chatroom.app.dao.user.UserRepository;
import com.chatroom.app.entity.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesDAO authoritiesDAO;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        com.chatroom.app.entity.User user = userService.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        List<Authorities> authoritiesList = authoritiesDAO.findByUsername(username);
        for (Authorities role : authoritiesList){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);

    }
}
