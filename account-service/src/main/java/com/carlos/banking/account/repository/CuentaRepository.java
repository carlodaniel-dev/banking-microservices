package com.carlos.banking.account.repository;

import com.carlos.banking.account.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByNumeroCuenta(Long numeroCuenta);

    boolean existsByNumeroCuenta(Long numeroCuenta);

    boolean existsByClienteId(Long clienteId);
}