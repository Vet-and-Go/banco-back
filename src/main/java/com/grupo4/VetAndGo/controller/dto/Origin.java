package com.grupo4.VetAndGo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Origin(
    @JsonProperty("cardNumber")
    @NotBlank String cardNumber,
    
    @JsonProperty("expirationDate")
    @Pattern(regexp = "^(\\d{2}/\\d{2}|\\d{4}-\\d{2})$", message = "Expiration date must be MM/YY or YYYY-MM")
    @NotBlank String expirationDate,
    
    @JsonProperty("cvc")
    @NotBlank String cvc,
    
    @JsonProperty("fullName")
    @NotBlank String fullName
) {}
