package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BankTransactionService {
    public List<BankTransaction> findAll();
    public List<BankTransaction> findByAccountId(Long accountId);
    public List<BankTransaction> findByAccountIdAndOrigin(Long accountId, TransactionOrigin origin);
    public List<BankTransaction> findByCardNumber(String cardNumber);
    public Optional<BankTransaction> findById(Long id);

    void createTransaction(BankAccount account, BigDecimal amount, String concept, TransactionType type, TransactionOrigin origin);
    void createTransaction(BankAccount account, BigDecimal amount, String concept, TransactionType type, TransactionOrigin origin, String cardNumber);
}

    
    
