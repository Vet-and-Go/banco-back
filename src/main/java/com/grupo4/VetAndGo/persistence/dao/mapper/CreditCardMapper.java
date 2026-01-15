package com.grupo4.VetAndGo.persistence.dao.mapper;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CreditCardJpaEntity;

public class CreditCardMapper {

    public static CreditCard toDomainCreditCard(CreditCardJpaEntity entity) {
        if (entity == null) return null;
        return new CreditCard(
                entity.getId(),
                entity.getCardNumber(),
                entity.getExpirationDate(),
                entity.getCvc(),
                entity.getFullName(),
                toDomainBankAccount(entity.getBankAccount())
        );
    }

    public static CreditCardJpaEntity fromDomainCreditCardtojpaEntity(CreditCard domain) {
        if (domain == null) return null;
        CreditCardJpaEntity entity = new CreditCardJpaEntity();
        entity.setId(domain.getId());
        entity.setCardNumber(domain.getCardNumber());
        entity.setExpirationDate(domain.getExpirationDate());
        entity.setCvc(domain.getCvc());
        entity.setFullName(domain.getFullName());
        entity.setBankAccount(fromDomainBankAccounttojpaEntity(domain.getBankAccount()));
        return entity;
    }

    private static BankAccount toDomainBankAccount(BankAccountJpaEntity entity) {
        if (entity == null) return null;
        BankAccount account = new BankAccount();
        account.setId(entity.getId());
        account.setIban(entity.getIban());
        account.setBalance(entity.getBalance());
        account.setClient(toDomainClient(entity.getClient()));
        return account;
    }

    private static Client toDomainClient(ClientJpaEntity entity) {
        if (entity == null) return null;
        Client client = new Client();
        client.setId(entity.getId());
        return client;
    }

    private static BankAccountJpaEntity fromDomainBankAccounttojpaEntity(BankAccount domain) {
        if (domain == null) return null;
        BankAccountJpaEntity entity = new BankAccountJpaEntity();
        entity.setId(domain.getId());
        return entity;
    }
}
