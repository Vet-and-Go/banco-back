package com.grupo4.VetAndGo.domain.service.impl;


import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;
import com.grupo4.VetAndGo.domain.repository.BankTransactionRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

import com.grupo4.VetAndGo.domain.service.BankTransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class BankTransactionServiceImpl implements BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;
    public BankTransactionServiceImpl(BankTransactionRepository bankTransactionRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
    }

    @Override
    public List<BankTransaction> findAll() {
        return bankTransactionRepository.findAll();
    }

    @Override
    public List<BankTransaction> findByAccountId(Long accountId) {
        return bankTransactionRepository.findByAccountId(accountId);
    }

    @Override
    public List<BankTransaction> findByAccountIdAndOrigin(Long accountId, TransactionOrigin origin) {
        return bankTransactionRepository.findByAccountIdAndOrigin(accountId, origin);
    }

    @Override
    public List<BankTransaction> findByCardNumber(String cardNumber) {
        return bankTransactionRepository.findByCardNumber(cardNumber);
    }

    @Override
    public Optional<BankTransaction> findById(Long id) {
        return bankTransactionRepository.findById(id);
    }

    @Override
    public void createTransaction(com.grupo4.VetAndGo.domain.model.BankAccount account, BigDecimal amount, String concept, TransactionType type, TransactionOrigin origin) {
        createTransaction(account, amount, concept, type, origin, null);
    }

    @Override
    public void createTransaction(com.grupo4.VetAndGo.domain.model.BankAccount account, BigDecimal amount, String concept, TransactionType type, TransactionOrigin origin, String cardNumber) {
        BankTransaction transaction = new BankTransaction();
        transaction.setBankAccount(account);
        transaction.setAmount(amount);
        transaction.setConcept(concept);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        transaction.setDate(LocalDateTime.now().format(formatter));
        transaction.setType(type);
        transaction.setOrigin(origin);
        transaction.setCardNumber(cardNumber);
        bankTransactionRepository.save(transaction);
    }


}
