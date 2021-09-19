package com.chatroom.app.dao.user;

import com.chatroom.app.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;

public interface UserRepository extends JpaRepository<User, Integer> {
}
