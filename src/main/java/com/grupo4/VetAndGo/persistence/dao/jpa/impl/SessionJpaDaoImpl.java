package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.SessionJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.SessionJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionJpaDaoImpl implements SessionJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String createSession(Long clientId) {
        String token = UUID.randomUUID().toString();
        SessionJpaEntity session = new SessionJpaEntity(token, clientId, LocalDateTime.now());
        entityManager.persist(session);
        return token;
    }

    @Override
    public Optional<SessionJpaEntity> findByToken(String token) {
        TypedQuery<SessionJpaEntity> query = entityManager.createQuery(
                "SELECT s FROM SessionJpaEntity s WHERE s.token = :token", SessionJpaEntity.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

    @Override
    @Transactional
    public void deleteByClientId(Long clientId) {
        entityManager.createQuery("DELETE FROM SessionJpaEntity s WHERE s.clientId = :clientId")
                .setParameter("clientId", clientId)
                .executeUpdate();
    }
}
