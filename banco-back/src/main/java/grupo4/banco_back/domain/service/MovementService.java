package grupo4.banco_back.domain.service;

import java.math.BigDecimal;
import java.util.Date;

public interface MovementService {
  // Are login & apiToken realted in database?
  Boolean validateStore(
      String login,
      String apiToken);

  // Can the origin account afford the payment?
  Boolean validateAblePayment(
      String cardNumber,
      Double amount);

  void findAll();
}
