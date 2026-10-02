package com.carlos.banking.account.service;

import com.carlos.banking.account.dto.MovimientoRequest;
import com.carlos.banking.account.dto.MovimientoResponse;
import com.carlos.banking.account.entity.Cuenta;
import com.carlos.banking.account.entity.Movimiento;
import com.carlos.banking.account.exception.CuentaNotFoundException;
import com.carlos.banking.account.mapper.MovimientoMapper;
import com.carlos.banking.account.repository.CuentaRepository;
import com.carlos.banking.account.repository.MovimientoRepository;
import com.carlos.banking.account.exception.SaldoNoDisponibleException;
import com.carlos.banking.account.exception.TipoMovimientoInvalidoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoMapper movimientoMapper;

    public MovimientoService(
            MovimientoRepository movimientoRepository,
            CuentaRepository cuentaRepository,
            MovimientoMapper movimientoMapper
    ) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.movimientoMapper = movimientoMapper;
    }

    @Transactional
    public MovimientoResponse registrarMovimiento(
            MovimientoRequest request
    ) {

        Cuenta cuenta = cuentaRepository
                .findByNumeroCuenta(request.numeroCuenta())
                .orElseThrow(() -> new CuentaNotFoundException(
                        "Cuenta no encontrada"
                ));

        BigDecimal saldoActual = cuenta.getSaldo();
        BigDecimal valor = request.valor();

        BigDecimal nuevoSaldo;

        if (request.tipoMovimiento().equalsIgnoreCase("Deposito")) {

            nuevoSaldo = saldoActual.add(valor);

        } else if (request.tipoMovimiento().equalsIgnoreCase("Retiro")) {

            if (valor.compareTo(saldoActual) > 0) {
                throw new SaldoNoDisponibleException(
                        "Saldo no disponible"
                );
            }

            nuevoSaldo = saldoActual.subtract(valor);

        } else {

            throw new TipoMovimientoInvalidoException(
                    "Tipo de movimiento no válido"
            );
        }

        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento();

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(request.tipoMovimiento());
        movimiento.setValor(valor);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        Movimiento movimientoGuardado =
                movimientoRepository.save(movimiento);

        return movimientoMapper.toResponse(movimientoGuardado);
    }
}