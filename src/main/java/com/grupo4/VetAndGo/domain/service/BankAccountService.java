package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.CardPayment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    List<BankAccount> findAll();
    Optional<BankAccount> findById(Long id);

    void deposit(String iban, BigDecimal amount);
    void withdraw(String iban, BigDecimal amount);
}
