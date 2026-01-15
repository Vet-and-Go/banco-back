package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.CreditCardJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CreditCardJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import java.util.List;
import java.util.Optional;


public class CreditCardJpaDaoImpl implements CreditCardJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CreditCardJpaEntity> findAll() {
        TypedQuery<CreditCardJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM CreditCardJpaEntity c", CreditCardJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<CreditCardJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(CreditCardJpaEntity.class, id));
    }

    @Override
    public Optional<CreditCardJpaEntity> findByCardNumber(String cardNumber) {
        TypedQuery<CreditCardJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM CreditCardJpaEntity c WHERE c.cardNumber = :cardNumber", CreditCardJpaEntity.class);
        query.setParameter("cardNumber", cardNumber);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<CreditCardJpaEntity> findByClientId(Long clientId) {
        TypedQuery<CreditCardJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM CreditCardJpaEntity c JOIN c.bankAccount b WHERE b.client.id = :clientId", CreditCardJpaEntity.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }


    @Override
    public CreditCardJpaEntity insert(CreditCardJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public CreditCardJpaEntity update(CreditCardJpaEntity jpaEntity) {
        return entityManager.merge(jpaEntity);
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(c) FROM CreditCardJpaEntity c", Long.class);
        return query.getSingleResult();
    }
}
