package com.example.service;

import com.example.models.Transaction;
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

    @Inject
    public Transaction createTransaction(Transaction transaction) {
        em.persist(transaction);
        return transaction;
    }
    public Transaction findTransaction(int id) {
        return em.find(Transaction.class, id);
    }
    public Transaction deleteTransaction(int id) {
        Transaction t = em.find(Transaction.class, id);
        em.remove(t);
        return t;
    }
    public Transaction updateTransaction(Transaction transaction, int id) {
        Transaction t = em.find(Transaction.class, id);
        t.setPrice(transaction.getPrice()) ;
        t.setLocalDate(transaction.getLocalDate());
        t.setCurrency(transaction.getCurrency());
        t.setType(transaction.getType());
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
        return transactions;
    }

}
