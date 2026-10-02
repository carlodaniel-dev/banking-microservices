package com.carlos.banking.account.dto;

import java.math.BigDecimal;
import java.util.List;

public record CuentaReporteResponse(
        Long numeroCuenta,
        String tipoCuenta,
        BigDecimal saldo,
        Boolean estado,
        List<MovimientoReporteResponse> movimientos
) {}