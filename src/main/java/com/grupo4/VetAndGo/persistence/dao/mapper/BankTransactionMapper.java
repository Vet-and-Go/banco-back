package com.grupo4.VetAndGo.persistence.dao.mapper;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankTransactionJpaEntity;

public class BankTransactionMapper {

    public static BankTransaction toDomainBankTransaction(BankTransactionJpaEntity entity) {
        if (entity == null) return null;
        return new BankTransaction(
                entity.getId(),
                entity.getDate(),
                entity.getAmount(),
                entity.getConcept(),
                entity.getType(),
                entity.getOrigin(),
                entity.getCardNumber(),
                toDomainBankAccount(entity.getBankAccount())
        );
    }

    public static BankTransactionJpaEntity fromDomainBankTransactiontojpaEntity(BankTransaction domain) {
        if (domain == null) return null;
        BankTransactionJpaEntity entity = new BankTransactionJpaEntity();
        entity.setId(domain.getId());
        entity.setDate(domain.getDate());
        entity.setAmount(domain.getAmount());
        entity.setConcept(domain.getConcept());
        entity.setType(domain.getType());
        entity.setOrigin(domain.getOrigin());
        entity.setCardNumber(domain.getCardNumber());
        entity.setBankAccount(fromDomainBankAccounttojpaEntity(domain.getBankAccount()));
        return entity;
    }

    private static BankAccount toDomainBankAccount(BankAccountJpaEntity entity) {
        if (entity == null) return null;
        BankAccount account = new BankAccount();
        account.setId(entity.getId());
        account.setIban(entity.getIban());
        return account;
    }

    private static BankAccountJpaEntity fromDomainBankAccounttojpaEntity(BankAccount domain) {
        if (domain == null) return null;
        BankAccountJpaEntity entity = new BankAccountJpaEntity();
        entity.setId(domain.getId());
        return entity;
    }
}
