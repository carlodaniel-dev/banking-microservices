package com.carlos.banking.customer.dto;

public record ClienteResponse(
        Long id,
        Long clienteId,
        String nombre,
        String genero,
        Integer edad,
        String identificacion,
        String direccion,
        String telefono,
        Boolean estado
) {
}