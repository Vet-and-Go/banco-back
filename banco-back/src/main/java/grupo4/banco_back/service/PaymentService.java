package grupo4.banco_back.service;

import java.util.Date;

public interface PaymentService {
  // Are login & apiToken valid?
  Boolean validateStore(
      String login,
      String apiToken);

  // Is the card valid?
  Boolean validateCard(
      String login,
      String apiToken,
      String cardNumber,
      Date expirationDate,
      String cvv,
      String fullName);
}
