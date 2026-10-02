package com.carlos.banking.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponse(
        Long id,
        LocalDateTime fecha,
        Long numeroCuenta,
        String tipoMovimiento,
        BigDecimal valor,
        BigDecimal saldo
) {}