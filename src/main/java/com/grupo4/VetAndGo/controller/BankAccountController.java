package com.grupo4.VetAndGo.controller;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.service.BankAccountService;
import com.grupo4.VetAndGo.domain.service.CardPaymentService;
import com.grupo4.VetAndGo.controller.dto.Payment;
import com.grupo4.VetAndGo.domain.model.CardPayment;
import com.grupo4.VetAndGo.controller.mapper.CardPaymentMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final CardPaymentService cardPaymentService;

    public BankAccountController(BankAccountService bankAccountService, CardPaymentService cardPaymentService) {
        this.bankAccountService = bankAccountService;
        this.cardPaymentService = cardPaymentService;
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> findAll() {
        return ResponseEntity.ok(bankAccountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> findById(@PathVariable Long id) {
        return bankAccountService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BankAccount>> findByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(bankAccountService.findByClientId(clientId));
    }







    @PostMapping(value = "/payment", consumes = "application/json")
    public ResponseEntity<Void> payWithCard(@RequestBody Payment request) {
        CardPayment cardPayment = CardPaymentMapper.toDomainCardPayment(request);
        cardPaymentService.processPayment(cardPayment);
        return ResponseEntity.ok().build();
    }
}
