package com.carlos.banking.customer.controller;

import com.carlos.banking.customer.dto.ClienteResponse;
import com.carlos.banking.customer.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Test
    void debeListarClientes() throws Exception {

        ClienteResponse cliente = new ClienteResponse(
                1L,
                1L,
                "Jose Lema",
                "M",
                30,
                "1234567890",
                "Quito",
                "0987654321",
                true
        );

        when(clienteService.listarClientes())
                .thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk());
    }

    @Test
    void debeObtenerClientePorId() throws Exception {

        ClienteResponse cliente = new ClienteResponse(
                1L,
                1L,
                "Jose Lema",
                "M",
                30,
                "1234567890",
                "Quito",
                "0987654321",
                true
        );

        when(clienteService.obtenerCliente(1L))
                .thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Jose Lema"))
                .andExpect(jsonPath("$.clienteId").value(1));
    }
}