package com.grupo4.VetAndGo.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.grupo4.VetAndGo.domain.mapper.BankAccountMapper;
import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.repository.BankAccountRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.BankAccountJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;

import jakarta.transaction.Transactional;

@Transactional
public class BankAccountRepositoryImpl implements BankAccountRepository {

  private final BankAccountJpaDao bankAccountJpaDao;

  public BankAccountRepositoryImpl(BankAccountJpaDao bankAccountJpaDao) {
    this.bankAccountJpaDao = bankAccountJpaDao;
  }

  @Override
  public List<BankAccount> findAll() {
    return bankAccountJpaDao.findAll().stream()
        .map(BankAccountMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<BankAccount> findById(Long id) {
    return bankAccountJpaDao.findById(id).map(BankAccountMapper::toDomain);
  }

  @Override
  public Optional<BankAccount> findByIban(String iban) {
    return bankAccountJpaDao.findByIban(iban).map(BankAccountMapper::toDomain);
  }

  @Override
  public BankAccount save(BankAccount bankAccount) {
    BankAccountJpaEntity entity = BankAccountMapper.toEntity(bankAccount);
    if (bankAccount.getId() == null) {
      return BankAccountMapper.toDomain(bankAccountJpaDao.insert(entity));
    } else {
      return BankAccountMapper.toDomain(bankAccountJpaDao.update(entity));
    }
  }

  @Override
  public void deleteById(Long id) {
    bankAccountJpaDao.deleteById(id);
  }
}
