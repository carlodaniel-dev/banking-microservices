package com.carlos.banking.account.mapper;

import com.carlos.banking.account.dto.CuentaRequest;
import com.carlos.banking.account.dto.CuentaResponse;
import com.carlos.banking.account.entity.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public Cuenta toEntity(CuentaRequest request) {
        Cuenta cuenta = new Cuenta();

        cuenta.setNumeroCuenta(request.numeroCuenta());
        cuenta.setTipoCuenta(request.tipoCuenta());
        cuenta.setSaldo(request.saldo());
        cuenta.setEstado(request.estado());
        cuenta.setClienteId(request.clienteId());

        return cuenta;
    }

    public CuentaResponse toResponse(Cuenta cuenta) {
        return new CuentaResponse(
                cuenta.getId(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldo(),
                cuenta.getEstado(),
                cuenta.getClienteId()
        );
    }
}