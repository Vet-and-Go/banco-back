package grupo4.banco_back.domain.service;

import java.util.Date;

public interface PaymentService {
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
  Boolean validateCorrectAccountRelation(
      String login,
      String destinationIban);

  // Can the origin account afford the payment?
  Boolean validatePossiblePayment(
      String cardNumber,
      Double amount);
}
