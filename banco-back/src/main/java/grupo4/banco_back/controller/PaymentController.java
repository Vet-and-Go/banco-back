package grupo4.banco_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupo4.banco_back.domain.service.PaymentService;
import grupo4.banco_back.domain.dto.PaymentDto;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("")
  public ResponseEntity<List<PaymentDto>> getAll() {
    return ResponseEntity.ok(paymentService.getAll());
  }
}
