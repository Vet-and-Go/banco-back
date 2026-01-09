package grupo4.banco_back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupo4.banco_back.domain.dto.PaymentDto;
import grupo4.banco_back.domain.service.cases.PaymentCase;

@RestController
@RequestMapping("/api/bank")
public class PaymentController {

  private final PaymentCase paymentCase;

  public PaymentController(PaymentCase paymentCase) {
    this.paymentCase = paymentCase;
  }

  @GetMapping("")
  public ResponseEntity<List<PaymentDto>> getAllPayments() {
    // List<PaymentDto> payments = paymentCase.getAll();
    // return ResponseEntity.ok(payments);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/payments")
  public ResponseEntity<Void> processPayment(@RequestBody PaymentDto paymentDto) {
    paymentCase.processPayment(paymentDto);
    return ResponseEntity.ok().build();
  }
}
