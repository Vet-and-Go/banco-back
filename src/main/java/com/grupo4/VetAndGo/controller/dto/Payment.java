package com.grupo4.VetAndGo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record Payment(
    @JsonProperty("authorization")
    @NotNull
    @Valid
    Authorization authorization,

    @JsonProperty("origin")
    @NotNull
    @Valid
    Origin origin,

    @JsonProperty("destination")
    @NotNull
    @Valid
    Destination destination,

    @JsonProperty("paymentDetails")
    @NotNull
    @Valid
    PaymentDetails paymentDetails
) {}