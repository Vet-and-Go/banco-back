package com.grupo4.VetAndGo.controller;

import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.service.CreditCardService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-cards")

public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping
    public ResponseEntity<List<CreditCard>> findAll() {
        return ResponseEntity.ok(creditCardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> findById(@PathVariable Long id) {
        return creditCardService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CreditCard>> findByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(creditCardService.findByClientId(clientId));
    }

    @GetMapping("/transactions/{cardId}")
    public ResponseEntity<List<BankTransaction>> getTransactions(@PathVariable Long cardId) {
        return ResponseEntity.ok(creditCardService.findTransactionsByCardId(cardId));
    }

    @GetMapping("/spending/{cardId}")
    public ResponseEntity<java.math.BigDecimal> getMonthlySpending(@PathVariable Long cardId) {
        return ResponseEntity.ok(creditCardService.calculateMonthlySpending(cardId));
    }
}
