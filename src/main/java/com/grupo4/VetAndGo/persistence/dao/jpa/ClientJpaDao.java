package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;

import java.util.Optional;

public interface ClientJpaDao extends GenericJpaDao<ClientJpaEntity> {
   Optional<ClientJpaEntity> findByLogin(String login);
}
