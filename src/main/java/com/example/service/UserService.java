package com.example.service;

import com.example.models.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Stateless
public class UserService {

    private static final Map<Integer, User> DB = new ConcurrentHashMap<>();
    @PersistenceContext
    EntityManager em;

    @Inject
    public User createUser(User user) {
        em.persist(user);
        return user;
    }
    public User findUser(int id) {
        return em.find(User.class, id);
    }
    public User deleteUser(int id) {
        return DB.remove(id);
    }
    /*public User updateUser(User user, int id) {
        return em.put(user, id);
    }
    public User findAllUser() {
        return em.find();
    }*/

}
