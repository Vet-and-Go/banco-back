package com.grupo4.VetAndGo.controller.dto;

public record LoginResponseDto(
    String token,
    String username,
    String role) {
}
