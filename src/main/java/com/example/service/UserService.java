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

@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;

    public UserService() {
        this.em = Persistence.getEntityManager();
    }

    @Transactional
    public User createUser(User user) {
        var tx = em.getTransaction();
        tx.begin();
        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hash);
        em.persist(user);
        tx.commit();
        em.close();
        return user;
    }

    public User findUser(int id) {
        User u = em.find(User.class, id);
        em.close();
        return u;
    }

    public User deleteUser(int id) {
        var tx = em.getTransaction();
        tx.begin();
        User u = em.find(User.class, id);
        em.remove(u);
        tx.commit();
        em.close();
        return u;
    }

    public User updateUser(User user, int id) {
        var tx = em.getTransaction();
        tx.begin();
        User u = em.find(User.class, id);
        u.setEmail(user.getEmail());
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        u.setBirthdate(user.getBirthdate());
        tx.commit();
        em.close();
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
        em.close();
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
