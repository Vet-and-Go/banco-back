package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.model.*;
import com.grupo4.VetAndGo.domain.repository.BankAccountRepository;
import com.grupo4.VetAndGo.domain.repository.BankTransactionRepository;

import com.grupo4.VetAndGo.domain.service.BankAccountService;
import com.grupo4.VetAndGo.domain.exception.*;
 

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionRepository bankTransactionRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankTransactionRepository bankTransactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionRepository = bankTransactionRepository;
    }

    @Override
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }



    @Override

    public void deposit(String iban, BigDecimal amount) {
        BankAccount account = bankAccountRepository.findByIban(iban)
                .orElseThrow(() -> new BussinesException("Account not found: " + iban));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BussinesException("Amount must be positive");
        }

        account.setBalance(account.getBalance().add(amount));
        bankAccountRepository.save(account);

        createTransaction(account, amount, "Deposit", TransactionType.CREDIT, TransactionOrigin.DIRECT_DEBIT);
    }

    @Override
    public void withdraw(String iban, BigDecimal amount) {
        BankAccount account = bankAccountRepository.findByIban(iban)
                .orElseThrow(() -> new BussinesException("Account not found: " + iban));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        bankAccountRepository.save(account);

        createTransaction(account, amount, "Withdrawal", TransactionType.DEBIT, TransactionOrigin.DIRECT_DEBIT);
    }






    private void createTransaction(BankAccount account, BigDecimal amount, String concept, TransactionType type, TransactionOrigin origin) {
        BankTransaction transaction = new BankTransaction();
        transaction.setBankAccount(account);
        transaction.setAmount(amount);
        transaction.setConcept(concept);
        transaction.setDate(LocalDateTime.now().toString());
        transaction.setType(type);
        transaction.setOrigin(origin);
        bankTransactionRepository.save(transaction);
    }
}
