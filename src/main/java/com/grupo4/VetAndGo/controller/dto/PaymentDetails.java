package com.grupo4.VetAndGo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PaymentDetails(
    @JsonProperty("amount")
    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    BigDecimal amount,
    
    @JsonProperty("concept")
    String concept
) {}
