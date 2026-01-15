package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.BankAccountJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import java.util.List;
import java.util.Optional;


public class BankAccountJpaDaoImpl implements BankAccountJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BankAccountJpaEntity> findAll() {
        TypedQuery<BankAccountJpaEntity> query = entityManager.createQuery(
                "SELECT b FROM BankAccountJpaEntity b", BankAccountJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<BankAccountJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(BankAccountJpaEntity.class, id));
    }

    @Override
    public Optional<BankAccountJpaEntity> findByIban(String iban) {
        TypedQuery<BankAccountJpaEntity> query = entityManager.createQuery(
                "SELECT b FROM BankAccountJpaEntity b WHERE b.iban = :iban", BankAccountJpaEntity.class);
        query.setParameter("iban", iban);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<BankAccountJpaEntity> findByClientId(Long clientId) {
        TypedQuery<BankAccountJpaEntity> query = entityManager.createQuery(
                "SELECT b FROM BankAccountJpaEntity b WHERE b.client.id = :clientId", BankAccountJpaEntity.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }


    @Override
    public BankAccountJpaEntity insert(BankAccountJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public BankAccountJpaEntity update(BankAccountJpaEntity jpaEntity) {
        return entityManager.merge(jpaEntity);
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(b) FROM BankAccountJpaEntity b", Long.class);
        return query.getSingleResult();
    }
}
