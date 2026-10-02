package com.carlos.banking.customer.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClienteTest {

    @Test
    void debeCrearClienteCorrectamente() {

        Cliente cliente = new Cliente();

        cliente.setClienteId(1L);
        cliente.setNombre("Jose Lema");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("1234567890");
        cliente.setDireccion("Otavalo sn y principal");
        cliente.setTelefono("0987654321");
        cliente.setContrasena("1234");
        cliente.setEstado(true);

        assertEquals(1L, cliente.getClienteId());
        assertEquals("Jose Lema", cliente.getNombre());
        assertEquals(30, cliente.getEdad());
        assertTrue(cliente.getEstado());
    }
}