package com.grupo4.VetAndGo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record Payment(
    @JsonProperty("autorizacion")
    @NotNull
    @Valid
    Authorization authorization,

    @JsonProperty("origen")
    @NotNull
    @Valid
    Origin origin,

    @JsonProperty("destino")
    @NotNull
    @Valid
    Destination destination,

    @JsonProperty("pago")
    @NotNull
    @Valid
    PaymentDetails paymentDetails
) {}