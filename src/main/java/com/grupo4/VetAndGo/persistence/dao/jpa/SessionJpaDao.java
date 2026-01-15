package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.SessionJpaEntity;
import java.util.Optional;

public interface SessionJpaDao {
    String createSession(Long clientId);
    Optional<SessionJpaEntity> findByToken(String token);
    void deleteByClientId(Long clientId);
}
