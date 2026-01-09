package grupo4.banco_back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupo4.banco_back.domain.dto.PaymentDto;
import grupo4.banco_back.domain.service.cases.PaymentService;

@RestController
@RequestMapping("/api/bank")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("")
  public ResponseEntity<List<PaymentDto>> getAll() {
  }
}
