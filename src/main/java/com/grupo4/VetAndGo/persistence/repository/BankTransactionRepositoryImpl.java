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
                .map(BankTransactionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BankTransaction> findById(Long id) {
        return bankTransactionJpaDao.findById(id).map(BankTransactionMapper::toDomain);
    }

    @Override
    public BankTransaction save(BankTransaction bankTransaction) {
        BankTransactionJpaEntity entity = BankTransactionMapper.toEntity(bankTransaction);
        if (bankTransaction.getId() == null) {
            return BankTransactionMapper.toDomain(bankTransactionJpaDao.insert(entity));
        } else {
            return BankTransactionMapper.toDomain(bankTransactionJpaDao.update(entity));
        }
    }

    @Override
    public void deleteById(Long id) {
        bankTransactionJpaDao.deleteById(id);
    }
}
