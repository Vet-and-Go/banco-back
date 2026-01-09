package grupo4.banco_back.domain.service.cases;

import grupo4.banco_back.domain.dto.PaymentDto;

public interface PaymentCase {

  void processPayment(PaymentDto paymentDto);

  // List<PaymentDto> getAll();
}
