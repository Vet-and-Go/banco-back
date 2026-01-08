package grupo4.banco_back.domain.repository;

import java.math.BigDecimal;

public interface PaymentRepository {
  void processPayment(
      String cardNumber,
      String destinationIban,
      BigDecimal amount,
      String concept);

  void findAll();
}
