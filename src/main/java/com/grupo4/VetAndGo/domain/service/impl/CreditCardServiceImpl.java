package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;
import com.grupo4.VetAndGo.domain.service.CreditCardService;

import java.util.List;
import java.util.Optional;

public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    @Override
    public Optional<CreditCard> findById(Long id) {
        return creditCardRepository.findById(id);
    }

    @Override
    public Optional<CreditCard> findByCardNumber(int cardNumber) {
        // Converting int to String to match repo method signature if needed, or adjust interface
        return creditCardRepository.findByCardNumber(String.valueOf(cardNumber));
    }


}
