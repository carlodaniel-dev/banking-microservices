package com.carlos.banking.account.service;

import com.carlos.banking.account.dto.CuentaReporteResponse;
import com.carlos.banking.account.dto.MovimientoReporteResponse;
import com.carlos.banking.account.dto.ReporteResponse;
import com.carlos.banking.account.entity.Cuenta;
import com.carlos.banking.account.entity.Movimiento;
import com.carlos.banking.account.repository.CuentaRepository;
import com.carlos.banking.account.repository.MovimientoRepository;
import com.carlos.banking.account.exception.FechaReporteInvalidaException;
import com.carlos.banking.account.exception.ClienteSinCuentasException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public ReporteService(
            CuentaRepository cuentaRepository,
            MovimientoRepository movimientoRepository
    ) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    public ReporteResponse generarReporte(
            Long clienteId,
            LocalDate fechaInicio,
            LocalDate fechaFin
    ) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new FechaReporteInvalidaException(
                    "La fecha de inicio no puede ser posterior a la fecha de fin"
            );
        }

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        if (cuentas.isEmpty()) {
            throw new ClienteSinCuentasException(
                    "El cliente no tiene cuentas registradas"
            );
        }

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.plusDays(1).atStartOfDay().minusNanos(1);

        List<CuentaReporteResponse> cuentasReporte = cuentas.stream()
                .map(cuenta -> construirCuentaReporte(
                        cuenta,
                        inicio,
                        fin
                ))
                .toList();

        return new ReporteResponse(
                clienteId,
                fechaInicio,
                fechaFin,
                cuentasReporte
        );
    }

    private CuentaReporteResponse construirCuentaReporte(
            Cuenta cuenta,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    ) {

        List<Movimiento> movimientos =
                movimientoRepository
                        .findByCuentaNumeroCuentaAndFechaBetween(
                                cuenta.getNumeroCuenta(),
                                fechaInicio,
                                fechaFin
                        );

        List<MovimientoReporteResponse> movimientosReporte =
                movimientos.stream()
                        .map(movimiento -> new MovimientoReporteResponse(
                                movimiento.getFecha(),
                                movimiento.getTipoMovimiento(),
                                movimiento.getValor(),
                                movimiento.getSaldo()
                        ))
                        .toList();

        return new CuentaReporteResponse(
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldo(),
                cuenta.getEstado(),
                movimientosReporte
        );
    }
}