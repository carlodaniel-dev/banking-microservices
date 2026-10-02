package com.carlos.banking.customer;

import com.carlos.banking.customer.entity.Cliente;
import com.carlos.banking.customer.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class ClienteIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:18");

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void debeGuardarYConsultarCliente() {

        Cliente cliente = new Cliente();

        cliente.setNombre("Cliente Integración");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("9999999999");
        cliente.setDireccion("Quito");
        cliente.setTelefono("0999999999");

        cliente.setClienteId(100L);
        cliente.setContrasena("123456");
        cliente.setEstado(true);

        Cliente guardado = clienteRepository.save(cliente);

        assertNotNull(guardado.getId());

        Cliente encontrado = clienteRepository
                .findById(guardado.getId())
                .orElseThrow();

        assertEquals("Cliente Integración", encontrado.getNombre());
        assertEquals("9999999999", encontrado.getIdentificacion());
        assertEquals(100L, encontrado.getClienteId());
        assertTrue(encontrado.getEstado());
    }
}