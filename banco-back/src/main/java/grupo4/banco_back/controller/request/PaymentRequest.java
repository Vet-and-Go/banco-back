package grupo4.banco_back.controller.request;

import java.math.BigDecimal;
import java.util.Date;

public record PaymentRequest(
    String login,
    String apiToken,
    String CardNumber,
    Date expirationDate,
    String cvv,
    String FullName,
    String destinationIban,
    BigDecimal amount,
    String concept

) {
}
