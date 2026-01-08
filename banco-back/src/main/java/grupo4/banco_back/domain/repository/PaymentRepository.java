package grupo4.banco_back.domain.repository;

import java.math.BigDecimal;
import java.util.Date;

public interface PaymentRepository {
  Boolean validateStore(
      String login,
      String apiToken);

  // Is the card in a valid format?
  Boolean validateCard(
      String login,
      String cardNumber,
      Date expirationDate,
      String cvv,
      String fullName);

  // Are login & Account related in database?
  Boolean validateAccountRelation(
      String login,
      String destinationIban);

  // Can the origin account afford the payment?
  Boolean validateAblePayment(
      String cardNumber,
      Double amount);

  void processPayment(
      String cardNumber,
      String destinationIban,
      BigDecimal amount,
      String concept);
}
