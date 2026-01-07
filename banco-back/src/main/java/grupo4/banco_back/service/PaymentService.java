package grupo4.banco_back.service;

import java.util.Date;

public interface PaymentService {
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

  // Can the origin account afford the payment?
  Boolean possiblePayment(
      String cardNumber,
      Double amount);
}
