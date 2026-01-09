package grupo4.banco_back.domain.service;

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
