package com.carlos.banking.account.service;

import com.carlos.banking.account.dto.CuentaRequest;
import com.carlos.banking.account.dto.CuentaResponse;
import com.carlos.banking.account.entity.Cuenta;
import com.carlos.banking.account.mapper.CuentaMapper;
import com.carlos.banking.account.repository.CuentaRepository;
import com.carlos.banking.account.exception.CuentaAlreadyExistsException;
import com.carlos.banking.account.exception.CuentaNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;

    public CuentaService(
            CuentaRepository cuentaRepository,
            CuentaMapper cuentaMapper
    ) {
        this.cuentaRepository = cuentaRepository;
        this.cuentaMapper = cuentaMapper;
    }

    public List<CuentaResponse> listarCuentas() {
        return cuentaRepository.findAll()
                .stream()
                .map(cuentaMapper::toResponse)
                .toList();
    }

    public CuentaResponse obtenerCuenta(Long numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada"));

        return cuentaMapper.toResponse(cuenta);
    }

    public CuentaResponse crearCuenta(CuentaRequest request) {
        if (cuentaRepository.existsByNumeroCuenta(request.numeroCuenta())) {
            throw new CuentaAlreadyExistsException(
                    "El número de cuenta ya existe"
            );
        }

        Cuenta cuenta = cuentaMapper.toEntity(request);

        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        return cuentaMapper.toResponse(cuentaGuardada);
    }

    public CuentaResponse actualizarCuenta(
            Long numeroCuenta,
            CuentaRequest request
    ) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException(
                        "Cuenta no encontrada"
                ));

        cuenta.setTipoCuenta(request.tipoCuenta());
        cuenta.setSaldo(request.saldo());
        cuenta.setEstado(request.estado());
        cuenta.setClienteId(request.clienteId());

        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        return cuentaMapper.toResponse(cuentaActualizada);
    }

    public void eliminarCuenta(Long numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException(
                        "Cuenta no encontrada"
                ));

        cuentaRepository.delete(cuenta);
    }
}