package com.example.service;
import com.example.config.Persistence;
import com.example.models.ConnectionHistory;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@Stateless
public class ConnectionHistoriesService {

    @PersistenceContext
    EntityManager em;

    public ConnectionHistoriesService() {
        this.em = Persistence.getEntityManager();
    }

    public List<ConnectionHistory> findAllConnectionHistories() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ConnectionHistory> cq = cb.createQuery(ConnectionHistory.class);
        Root<ConnectionHistory> root = cq.from(ConnectionHistory.class);
        cq.select(root);
        List<ConnectionHistory> results = em.createQuery(cq).getResultList();
        em.close();
        return results;
    }

    public List<ConnectionHistory> findAllConnectionHistoriesOfUser(int idUser) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ConnectionHistory> cq = cb.createQuery(ConnectionHistory.class);
        Root<ConnectionHistory> root = cq.from(ConnectionHistory.class);
        cq.select(root).where(cb.equal(root.get("user").get("id"), idUser));
        List<ConnectionHistory> results = em.createQuery(cq).getResultList();
        em.close();
        return results;
    }
}
