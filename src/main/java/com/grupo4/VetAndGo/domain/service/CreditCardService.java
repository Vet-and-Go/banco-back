package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


public interface CreditCardService {
    public List<CreditCard> findAll();
    public Optional<CreditCard> findById(Long id);
    public Optional<CreditCard> findByCardNumber(int cardNumber);

}
