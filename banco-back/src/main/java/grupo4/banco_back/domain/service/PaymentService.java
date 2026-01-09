package grupo4.banco_back.domain.service;

import grupo4.banco_back.domain.dto.PaymentDto;

public interface PaymentService {

  void processPayment(PaymentDto paymentDto);

  // List<PaymentDto> getAll();
}
