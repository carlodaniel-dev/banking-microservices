package com.carlos.banking.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoReporteResponse(
        LocalDateTime fecha,
        String tipoMovimiento,
        BigDecimal valor,
        BigDecimal saldo
) {}