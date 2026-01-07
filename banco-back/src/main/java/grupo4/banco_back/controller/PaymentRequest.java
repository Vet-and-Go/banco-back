package grupo4.banco_back.controller;

import java.math.BigDecimal;

public record PaymentRequest(
    String login,
    String apiToken,
    String origin,
    String destination,
    BigDecimal amount,
    String concept) {
}
