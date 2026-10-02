package com.carlos.banking.account.dto;

import java.math.BigDecimal;

public record CuentaResponse(
        Long id,
        Long numeroCuenta,
        String tipoCuenta,
        BigDecimal saldo,
        Boolean estado,
        Long clienteId
) {
}