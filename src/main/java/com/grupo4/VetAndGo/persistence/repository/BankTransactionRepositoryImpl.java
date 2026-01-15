package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.repository.BankTransactionRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.BankTransactionJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankTransactionJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.mapper.BankTransactionMapper;
import jakarta.transaction.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
public class BankTransactionRepositoryImpl implements BankTransactionRepository {

    private final BankTransactionJpaDao bankTransactionJpaDao;
    public BankTransactionRepositoryImpl(BankTransactionJpaDao bankTransactionJpaDao) {
        this.bankTransactionJpaDao = bankTransactionJpaDao;
    }

    @Override
    public List<BankTransaction> findAll() {
        return bankTransactionJpaDao.findAll().stream()
                .map(BankTransactionMapper::toDomainBankTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public List<BankTransaction> findByAccountId(Long accountId) {
        return bankTransactionJpaDao.findByAccountId(accountId).stream()
                .map(BankTransactionMapper::toDomainBankTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public List<BankTransaction> findByAccountIdAndOrigin(Long accountId, com.grupo4.VetAndGo.domain.model.TransactionOrigin origin) {
        return bankTransactionJpaDao.findByAccountIdAndOrigin(accountId, origin).stream()
                .map(BankTransactionMapper::toDomainBankTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public List<BankTransaction> findByCardNumber(String cardNumber) {
        return bankTransactionJpaDao.findByCardNumber(cardNumber).stream()
                .map(BankTransactionMapper::toDomainBankTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BankTransaction> findById(Long id) {
        return bankTransactionJpaDao.findById(id).map(BankTransactionMapper::toDomainBankTransaction);
    }

    @Override
    public BankTransaction save(BankTransaction bankTransaction) {
        BankTransactionJpaEntity entity = BankTransactionMapper.fromDomainBankTransactiontojpaEntity(bankTransaction);
        if (bankTransaction.getId() == null) {
            return BankTransactionMapper.toDomainBankTransaction(bankTransactionJpaDao.insert(entity));
        } else {
            return BankTransactionMapper.toDomainBankTransaction(bankTransactionJpaDao.update(entity));
        }
    }
}
