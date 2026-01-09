package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.dto.Payment;
import com.grupo4.VetAndGo.domain.model.CardPayment;

public class CardPaymentMapper {

    public static CardPayment toDomain(Payment dto) {
        if (dto == null) {
            return null;
        }

        return CardPayment.builder()
                .destinationIban(dto.destination() != null ? dto.destination().iban() : null)
                .amount(dto.paymentDetails() != null ? dto.paymentDetails().amount() : null)
                .concept(dto.paymentDetails() != null ? dto.paymentDetails().concept() : null)
                .cardNumber(dto.origin() != null ? dto.origin().cardNumber() : null)
                .cardExpirationDate(dto.origin() != null ? dto.origin().cardExpirationDate() : null)
                .cardCvv(dto.origin() != null ? dto.origin().cardCvv() : null)
                .build();
    }
}
