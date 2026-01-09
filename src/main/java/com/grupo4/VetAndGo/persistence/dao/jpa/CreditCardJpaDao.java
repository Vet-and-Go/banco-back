package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CreditCardJpaEntity;

import java.util.Optional;

public interface CreditCardJpaDao extends GenericJpaDao<CreditCardJpaEntity> {
    Optional<CreditCardJpaEntity> findByCardNumber(String cardNumber);
}
