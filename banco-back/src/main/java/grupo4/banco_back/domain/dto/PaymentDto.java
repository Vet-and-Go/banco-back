package grupo4.banco_back.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

public record PaymentDto(

    String login,
    String apiToken,
    String CardNumber,
    Date expirationDate,
    String cvv,
    String fullName,
    String destinationIban,
    BigDecimal amount,
    String concept

) {
  public PaymentDto(
      String login,
      String apiToken,
      String CardNumber,
      Date expirationDate,
      String cvv,
      String fullName,
      String destinationIban,
      BigDecimal amount,
      String concept) {
    this.login = login;
    this.apiToken = apiToken;
    this.CardNumber = CardNumber;
    this.expirationDate = expirationDate;
    this.cvv = cvv;
    this.fullName = fullName;
    this.destinationIban = destinationIban;
    this.amount = amount;
    this.concept = concept;
  }
}
