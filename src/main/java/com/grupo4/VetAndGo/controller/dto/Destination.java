package com.grupo4.VetAndGo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Destination(
    @NotBlank
    @Pattern(regexp = "^ES.*", message = "Destination IBAN must start with 'ES'")
    String iban
) {}
