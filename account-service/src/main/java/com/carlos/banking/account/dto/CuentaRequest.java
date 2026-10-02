package com.carlos.banking.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CuentaRequest(

        @NotNull(message = "El número de cuenta es obligatorio")
        @Positive(message = "El número de cuenta debe ser positivo")
        Long numeroCuenta,

        @NotNull(message = "El tipo de cuenta es obligatorio")
        String tipoCuenta,

        @NotNull(message = "El saldo es obligatorio")
        @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
        BigDecimal saldo,

        @NotNull(message = "El estado es obligatorio")
        Boolean estado,

        @NotNull(message = "El clienteId es obligatorio")
        @Positive(message = "El clienteId debe ser positivo")
        Long clienteId
) {
}