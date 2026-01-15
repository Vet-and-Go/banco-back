package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.CreditCardJpaDao;
import com.grupo4.VetAndGo.persistence.dao.mapper.CreditCardMapper;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class CreditCardRepositoryImpl implements CreditCardRepository {

    private final CreditCardJpaDao creditCardJpaDao;
    public CreditCardRepositoryImpl(CreditCardJpaDao creditCardJpaDao) {
        this.creditCardJpaDao = creditCardJpaDao;
    }

    @Override
    public List<CreditCard> findAll() {
        return creditCardJpaDao.findAll().stream()
                .map(CreditCardMapper::toDomainCreditCard)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CreditCard> findById(Long id) {
        return creditCardJpaDao.findById(id).map(CreditCardMapper::toDomainCreditCard);
    }

    @Override
    public Optional<CreditCard> findByCardNumber(String cardNumber) {
        return creditCardJpaDao.findByCardNumber(cardNumber).map(CreditCardMapper::toDomainCreditCard);
    }

    @Override
    public List<CreditCard> findByClientId(Long clientId) {
        return creditCardJpaDao.findByClientId(clientId).stream()
                .map(CreditCardMapper::toDomainCreditCard)
                .collect(Collectors.toList());
    }
}
