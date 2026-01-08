package grupo4.banco_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupo4.banco_back.controller.request.PaymentRequest;
import grupo4.banco_back.domain.service.PaymentService;

@RestController
@RequestMapping("/api/bank")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("")
  public PaymentRequest getAll() {
    return null;
  }
}
