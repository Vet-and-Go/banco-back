package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.domain.model.CreditCard;
import java.util.List;
import java.util.Optional;

public interface CreditCardRepository {
    List<CreditCard> findAll();
    Optional<CreditCard> findById(Long id);
    Optional<CreditCard> findByCardNumber(String cardNumber);
    CreditCard save(CreditCard creditCard);
    void deleteById(Long id);
}
