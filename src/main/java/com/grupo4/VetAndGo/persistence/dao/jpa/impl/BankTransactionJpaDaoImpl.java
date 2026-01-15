package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.BankTransactionJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankTransactionJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import java.util.List;
import java.util.Optional;


public class BankTransactionJpaDaoImpl implements BankTransactionJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BankTransactionJpaEntity> findAll() {
        TypedQuery<BankTransactionJpaEntity> query = entityManager.createQuery(
                "SELECT t FROM BankTransactionJpaEntity t", BankTransactionJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public List<BankTransactionJpaEntity> findByAccountId(Long accountId) {
        TypedQuery<BankTransactionJpaEntity> query = entityManager.createQuery(
                "SELECT t FROM BankTransactionJpaEntity t WHERE t.bankAccount.id = :accountId", BankTransactionJpaEntity.class);
        query.setParameter("accountId", accountId);
        return query.getResultList();
    }

    @Override
    public List<BankTransactionJpaEntity> findByAccountIdAndOrigin(Long accountId, com.grupo4.VetAndGo.domain.model.TransactionOrigin origin) {
        TypedQuery<BankTransactionJpaEntity> query = entityManager.createQuery(
                "SELECT t FROM BankTransactionJpaEntity t WHERE t.bankAccount.id = :accountId AND t.origin = :origin", BankTransactionJpaEntity.class);
        query.setParameter("accountId", accountId);
        query.setParameter("origin", origin);
        return query.getResultList();
    }

    @Override
    public List<BankTransactionJpaEntity> findByCardNumber(String cardNumber) {
        TypedQuery<BankTransactionJpaEntity> query = entityManager.createQuery(
                "SELECT t FROM BankTransactionJpaEntity t WHERE t.cardNumber = :cardNumber", BankTransactionJpaEntity.class);
        query.setParameter("cardNumber", cardNumber);
        return query.getResultList();
    }

    @Override
    public Optional<BankTransactionJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(BankTransactionJpaEntity.class, id));
    }


    @Override
    public BankTransactionJpaEntity insert(BankTransactionJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public BankTransactionJpaEntity update(BankTransactionJpaEntity jpaEntity) {
        return entityManager.merge(jpaEntity);
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(t) FROM BankTransactionJpaEntity t", Long.class);
        return query.getSingleResult();
    }
}
