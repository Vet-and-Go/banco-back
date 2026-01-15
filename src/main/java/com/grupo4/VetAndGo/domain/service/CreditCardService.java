package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.CreditCard;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;


public interface CreditCardService {
    public List<CreditCard> findAll();
    public Optional<CreditCard> findById(Long id);
    public Optional<CreditCard> findByCardNumber(String cardNumber);
    List<CreditCard> findByClientId(Long clientId);
    List<BankTransaction> findTransactionsByCardId(Long cardId);
    BigDecimal calculateMonthlySpending(Long cardId);
}

