package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import java.util.Optional;

public interface BankAccountJpaDao extends GenericJpaDao<BankAccountJpaEntity> {
    Optional<BankAccountJpaEntity> findByIban(String iban);
}
