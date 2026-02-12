package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.dto.Payment;
import com.grupo4.VetAndGo.domain.model.CardPayment;

public class CardPaymentMapper {

    public static CardPayment toDomainCardPayment(Payment dto) {
        if (dto == null) {
            return null;
        }

        return new CardPayment(
                dto.destination().iban(),
                dto.paymentDetails().amount(),
                dto.paymentDetails().concept(),
                dto.origin().cardNumber(),
                dto.origin().expirationDate(),
                dto.origin().cvc(),
                dto.origin().fullName()
        );
    }
}
