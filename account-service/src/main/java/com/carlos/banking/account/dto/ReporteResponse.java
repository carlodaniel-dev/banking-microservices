package com.carlos.banking.account.dto;

import java.time.LocalDate;
import java.util.List;

public record ReporteResponse(
        Long clienteId,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        List<CuentaReporteResponse> cuentas
) {}