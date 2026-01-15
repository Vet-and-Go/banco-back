package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.repository.BankAccountRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.BankAccountJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.mapper.BankAccountMapper;
import jakarta.transaction.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
public class BankAccountRepositoryImpl implements BankAccountRepository {

    private final BankAccountJpaDao bankAccountJpaDao;
    public BankAccountRepositoryImpl(BankAccountJpaDao bankAccountJpaDao) {
        this.bankAccountJpaDao = bankAccountJpaDao;
    }

    @Override
    public List<BankAccount> findAll() {
        return bankAccountJpaDao.findAll().stream()
                .map(BankAccountMapper::toDomainBankAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountJpaDao.findById(id).map(BankAccountMapper::toDomainBankAccount);
    }

    @Override
    public Optional<BankAccount> findByIban(String iban) {
        return bankAccountJpaDao.findByIban(iban).map(BankAccountMapper::toDomainBankAccount);
    }

    @Override
    public List<BankAccount> findByClientId(Long clientId) {
        return bankAccountJpaDao.findByClientId(clientId).stream()
                .map(BankAccountMapper::toDomainBankAccount)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        BankAccountJpaEntity entity = BankAccountMapper.fromDomainAccounttojpaEntity(bankAccount);
        if (bankAccount.getId() == null) {
            return BankAccountMapper.toDomainBankAccount(bankAccountJpaDao.insert(entity));
        } else {
            return BankAccountMapper.toDomainBankAccount(bankAccountJpaDao.update(entity));
        }
    }
}
