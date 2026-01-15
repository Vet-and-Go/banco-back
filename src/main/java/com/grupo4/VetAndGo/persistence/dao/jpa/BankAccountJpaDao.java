package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;

import java.util.List;
import java.util.Optional;

public interface BankAccountJpaDao extends GenericJpaDao<BankAccountJpaEntity> {
    Optional<BankAccountJpaEntity> findByIban(String iban);
    List<BankAccountJpaEntity> findByClientId(Long clientId);
}
