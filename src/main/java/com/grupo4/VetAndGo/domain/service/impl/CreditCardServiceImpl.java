package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;
import com.grupo4.VetAndGo.domain.service.BankTransactionService;
import com.grupo4.VetAndGo.domain.service.CreditCardService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.grupo4.VetAndGo.domain.model.TransactionType;

public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final BankTransactionService bankTransactionService;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository, BankTransactionService bankTransactionService) {
        this.creditCardRepository = creditCardRepository;
        this.bankTransactionService = bankTransactionService;
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
    public Optional<CreditCard> findByCardNumber(String cardNumber) {
        return creditCardRepository.findByCardNumber(cardNumber);
    }

    @Override
    public List<CreditCard> findByClientId(Long clientId) {
        return creditCardRepository.findByClientId(clientId);
    }

    @Override
    public List<BankTransaction> findTransactionsByCardId(Long cardId) {
        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new ValidationException("Card not found"));
        
        return bankTransactionService.findByCardNumber(card.getCardNumber());
    }

    @Override
    public BigDecimal calculateMonthlySpending(Long cardId) {
        List<BankTransaction> transactions = findTransactionsByCardId(cardId);
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.DEBIT)
                .filter(t -> {
                    try {
                        return LocalDateTime.parse(t.getDate()).isAfter(oneMonthAgo);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .map(BankTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
