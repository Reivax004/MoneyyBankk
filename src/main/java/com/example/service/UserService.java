package com.example.service;

import com.example.config.Persistence;
import com.example.models.Transaction;
import com.example.models.User;

import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

@Singleton
public class UserService {

    @PersistenceContext
    EntityManager em;

    public UserService() {
        this.em = Persistence.getEntityManager();
    }

    @Transactional
    public User createUser(User user) {
        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hash);
        em.persist(user);
        return user;
    }

    public User findUser(int id) {

        return em.find(User.class, id);
    }

    public User deleteUser(int id) {
        User u = em.find(User.class, id);
        em.remove(u);
        return u;
    }

    public User updateUser(User user, int id) {
        User u = em.find(User.class, id);
        u.setEmail(user.getEmail());
        ;
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        // u.setBirthdate(user.getBirthdate());
        return u;
    }

    public List<User> findAllUser() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        List<User> users = em.createQuery(cq).getResultList();
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }

    public User getUserByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root)
                .where(cb.equal(root.get("email"), email));

        return em.createQuery(cq).getSingleResult();
    }

}
