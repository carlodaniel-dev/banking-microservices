package com.carlos.banking.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cliente extends Persona {
    @Column(name = "cliente_id", unique = true, nullable = false)
    private Long clienteId;

    private String contrasena;

    private Boolean estado;
}