package com.grupo4.VetAndGo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record Authorization(
    @NotBlank String login,
    @JsonProperty("api_token")
    @NotBlank String apiToken
) {}
