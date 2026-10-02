package com.carlos.banking.account.repository;

import com.carlos.banking.account.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaNumeroCuenta(Long numeroCuenta);
}