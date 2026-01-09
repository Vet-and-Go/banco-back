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

import java.math.BigDecimal;
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



    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody AmountRequest request) {
        bankAccountService.deposit(request.iban(), request.amount());
        return ResponseEntity.ok().build();
    }

    public record AmountRequest(String iban, BigDecimal amount) {}

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody AmountRequest request) {
        bankAccountService.withdraw(request.iban(), request.amount());
        return ResponseEntity.ok().build();
    }



    @PostMapping(value = "/payment", consumes = "application/json")
    public ResponseEntity<Void> payWithCard(@RequestBody Payment request) {
        CardPayment cardPayment = CardPaymentMapper.toDomain(request);
        cardPaymentService.processPayment(cardPayment);
        return ResponseEntity.ok().build();
    }
}
