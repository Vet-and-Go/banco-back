package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.ClientJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import java.util.List;
import java.util.Optional;


public class ClientJpaDaoImpl implements ClientJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ClientJpaEntity> findAll() {
        TypedQuery<ClientJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM ClientJpaEntity c", ClientJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<ClientJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(ClientJpaEntity.class, id));
    }

    @Override
    public ClientJpaEntity insert(ClientJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public ClientJpaEntity update(ClientJpaEntity jpaEntity) {
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        ClientJpaEntity entity = entityManager.find(ClientJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(c) FROM ClientJpaEntity c", Long.class);
        return query.getSingleResult();
    }
}
