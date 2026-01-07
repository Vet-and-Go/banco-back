package grupo4.banco_back.controller.mapper;

import grupo4.banco_back.domain.dto.PaymentDto;

public class Mapper {
  public PaymentDto fromPaymentRequestToPaymentDto(
      String login,
      String apiToken,
      String CardNumber,
      java.util.Date expirationDate,
      String cvv,
      String FullName,
      String destinationIban,
      java.math.BigDecimal amount,
      String concept) {

    return new PaymentDto(
        login,
        apiToken,
        CardNumber,
        expirationDate,
        cvv,
        FullName,
        destinationIban,
        amount,
        concept);
  }
}
