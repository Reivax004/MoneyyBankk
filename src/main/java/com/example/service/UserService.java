package com.example.service;

import com.example.models.User;

import java.sql.Date;

import com.example.messaging.UserCreatedProducer;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;

    @Inject
    UserCreatedProducer producer;
    public User createUser(String lastname,String firstname, String email, Date birthdate, String password) {
        User u = new User(lastname,firstname, email, birthdate, password);
        em.persist(u);
        producer.sendUserCreatedEvent(u);
        return u;
    }

    public User findUser(Long id) {
        return em.find(User.class, id);
    }
}
