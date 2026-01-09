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
    public void deleteById(Long id) {
        BankTransactionJpaEntity entity = entityManager.find(BankTransactionJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(t) FROM BankTransactionJpaEntity t", Long.class);
        return query.getSingleResult();
    }
}
