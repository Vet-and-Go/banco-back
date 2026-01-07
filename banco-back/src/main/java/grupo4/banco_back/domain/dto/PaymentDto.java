package grupo4.banco_back.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

public record PaymentDto(

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
