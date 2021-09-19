package com.chatroom.app.dao.authorities;

import com.chatroom.app.entity.Authorities;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class AuthoritiesDAOImpl implements AuthoritiesDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Authorities> findByUsername(String username) {

        Session session = entityManager.unwrap(Session.class);
        Query<Authorities> query = session.createQuery("from Authorities where username = :username", Authorities.class);

        query.setParameter("username", username);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Authorities a) {
        entityManager.merge(a);
    }

    @Override
    @Transactional
    public void delete(String username) {

        Session session = entityManager.unwrap(Session.class);
        Query<Authorities> q = session.createQuery("delete from Authorities where username = :username");
        q.setParameter("username", username);
        q.executeUpdate();
    }
}
