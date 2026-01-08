package grupo4.banco_back.domain.service;

import java.math.BigDecimal;
import java.util.Date;

public interface MovementService {
  // - Daniel
  // Methods are arranged from most important to least important.
  // Are login & apiToken realted in database?
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

  void findAll();
}
