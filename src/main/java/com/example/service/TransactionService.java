package com.example.service;

import com.example.config.Persistence;
import com.example.models.Transaction;
import com.example.models.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Stateless
public class TransactionService {

    @PersistenceContext
    EntityManager em;

    public TransactionService() {
        this.em = Persistence.getEntityManager();
    }

    public Transaction createTransaction(Transaction transaction) {
        var tx = em.getTransaction();
        tx.begin();
        em.persist(transaction);
        tx.commit();
        em.close();
        return transaction;
    }

    public Transaction findTransaction(int id) {
        Transaction transaction = em.find(Transaction.class, id);
        em.close();
        return transaction;
    }

    public Transaction deleteTransaction(int id) {
        var tx = em.getTransaction();
        tx.begin();
        Transaction t = em.find(Transaction.class, id);
        if (t == null) {
            tx.rollback();
            em.close();
            return null;
        }
        em.remove(t);
        tx.commit();
        em.close();
        return t;
    }

    public Transaction updateTransaction(Transaction transaction, int id) {
        var tx = em.getTransaction();
        tx.begin();
        Transaction t = em.find(Transaction.class, id);
        t.setPrice(transaction.getPrice());
        t.setDate(transaction.getDate());
        t.setCurrency(transaction.getCurrency());
        t.setType(transaction.getType());
        tx.commit();
        em.close();
        return t;
    }

    public List<Transaction> findAllTransactionOfUser(int idUser) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
        Root<Transaction> root = cq.from(Transaction.class);
        cq.select(root).where(cb.equal(root.get("user").get("id"), idUser));
        List<Transaction> transactions = em.createQuery(cq).getResultList();
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        em.close();
        return transactions;
    }

}
