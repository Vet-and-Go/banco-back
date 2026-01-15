package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankTransactionJpaEntity;

import java.util.List;

public interface BankTransactionJpaDao extends GenericJpaDao<BankTransactionJpaEntity> {
    List<BankTransactionJpaEntity> findByAccountId(Long accountId);
    List<BankTransactionJpaEntity> findByAccountIdAndOrigin(Long accountId, com.grupo4.VetAndGo.domain.model.TransactionOrigin origin);
    List<BankTransactionJpaEntity> findByCardNumber(String cardNumber);
}
