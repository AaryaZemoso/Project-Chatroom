package com.chatroom.app.dao.authorities;

import com.chatroom.app.entity.Authorities;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AuthoritiesDAO {
    List<Authorities> findByUsername(String username);
    void save(Authorities a);
    void delete(String username);
}
