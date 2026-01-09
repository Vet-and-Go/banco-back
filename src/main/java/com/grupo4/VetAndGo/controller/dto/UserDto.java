package com.grupo4.VetAndGo.controller.dto;

public record UserDto(
    Long id,
    String username,
    String password,
    Role role) {
}
