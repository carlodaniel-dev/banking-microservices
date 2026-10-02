package com.carlos.banking.customer.repository;

import com.carlos.banking.customer.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByClienteId(Long clienteId);

    boolean existsByIdentificacion(String identificacion);

    boolean existsByClienteId(Long clienteId);

    boolean existsByIdentificacionAndClienteIdNot(String identificacion, Long clienteId);
}