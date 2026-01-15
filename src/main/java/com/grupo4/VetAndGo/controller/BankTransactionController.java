package com.grupo4.VetAndGo.controller;

import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.service.BankTransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bank-transactions")
public class BankTransactionController {

    private final BankTransactionService bankTransactionService;

    public BankTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @GetMapping
    public ResponseEntity<List<BankTransaction>> findAll() {
        return ResponseEntity.ok(bankTransactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankTransaction> findById(@PathVariable Long id) {
        return bankTransactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<BankTransaction>> findByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(bankTransactionService.findByAccountId(accountId));
    }
}
