package com.grupo4.VetAndGo.controller.webmodel;

import com.grupo4.VetAndGo.domain.model.Role;

public record UserInsert(
    String username,
    String password,
    Role role) {
}
