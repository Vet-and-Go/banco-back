package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.repository.BankTransactionRepository;

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
    public Optional<BankTransaction> findById(Long id) {
        return bankTransactionRepository.findById(id);
    }


}
