package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.model.BankTransaction;
import java.util.List;
import java.util.Optional;

public interface BankTransactionService {
    public List<BankTransaction> findAll();
    public Optional<BankTransaction> findById(Long id);

}

    
    
