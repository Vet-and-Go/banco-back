package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.model.CardPayment;

public interface CardPaymentService {
    void processPayment(CardPayment payment);
}
