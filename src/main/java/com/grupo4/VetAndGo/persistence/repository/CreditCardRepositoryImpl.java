package com.grupo4.VetAndGo.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.grupo4.VetAndGo.domain.mapper.CreditCardMapper;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.CreditCardJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CreditCardJpaEntity;

import jakarta.transaction.Transactional;

@Transactional
public class CreditCardRepositoryImpl implements CreditCardRepository {

  private final CreditCardJpaDao creditCardJpaDao;

  public CreditCardRepositoryImpl(CreditCardJpaDao creditCardJpaDao) {
    this.creditCardJpaDao = creditCardJpaDao;
  }

  @Override
  public List<CreditCard> findAll() {
    return creditCardJpaDao.findAll().stream()
        .map(CreditCardMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<CreditCard> findById(Long id) {
    return creditCardJpaDao.findById(id).map(CreditCardMapper::toDomain);
  }

  @Override
  public Optional<CreditCard> findByCardNumber(String cardNumber) {
    return creditCardJpaDao.findByCardNumber(cardNumber).map(CreditCardMapper::toDomain);
  }

  @Override
  public CreditCard save(CreditCard creditCard) {
    CreditCardJpaEntity entity = CreditCardMapper.toEntity(creditCard);
    if (creditCard.getId() == null) {
      return CreditCardMapper.toDomain(creditCardJpaDao.insert(entity));
    } else {
      return CreditCardMapper.toDomain(creditCardJpaDao.update(entity));
    }
  }

  @Override
  public void deleteById(Long id) {
    creditCardJpaDao.deleteById(id);
  }
}
