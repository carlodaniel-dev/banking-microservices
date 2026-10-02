package com.carlos.banking.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MovimientoRequest(

        @NotNull(message = "El número de cuenta es obligatorio")
        @Positive(message = "El número de cuenta debe ser positivo")
        Long numeroCuenta,

        @NotBlank(message = "El tipo de movimiento es obligatorio")
        String tipoMovimiento,

        @NotNull(message = "El valor es obligatorio")
        @DecimalMin(value = "0.01", message = "El valor debe ser mayor a cero")
        BigDecimal valor
) {}