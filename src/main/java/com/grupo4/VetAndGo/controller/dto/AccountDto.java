package com.grupo4.VetAndGo.controller.dto;


import java.math.BigDecimal;

public record AccountDto (
     String iban,
     BigDecimal balance,
     String owner,
     String type,
     String currency
){}
