package com.grupo4.VetAndGo.persistence.dao.mapper;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CreditCardJpaEntity;

public class CreditCardMapper {

    public static CreditCard toDomain(CreditCardJpaEntity entity) {
        if (entity == null) return null;
        return new CreditCard(
                entity.getId(),
                entity.getCardNumber(),
                entity.getExpirationDate(),
                entity.getCvc(),
                entity.getFullName(),
                toDomainAccount(entity.getBankAccount())
        );
    }

    public static CreditCardJpaEntity toEntity(CreditCard domain) {
        if (domain == null) return null;
        CreditCardJpaEntity entity = new CreditCardJpaEntity();
        entity.setId(domain.getId());
        entity.setCardNumber(domain.getCardNumber());
        entity.setExpirationDate(domain.getExpirationDate());
        entity.setCvc(domain.getCvc());
        entity.setFullName(domain.getFullName());
        entity.setBankAccount(toEntityAccount(domain.getBankAccount()));
        return entity;
    }

    private static BankAccount toDomainAccount(BankAccountJpaEntity entity) {
        if (entity == null) return null;
        BankAccount account = new BankAccount();
        account.setId(entity.getId());
        account.setIban(entity.getIban());
        account.setBalance(entity.getBalance());
        return account;
    }

    private static BankAccountJpaEntity toEntityAccount(BankAccount domain) {
        if (domain == null) return null;
        BankAccountJpaEntity entity = new BankAccountJpaEntity();
        entity.setId(domain.getId());
        return entity;
    }
}
