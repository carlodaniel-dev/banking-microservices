package com.carlos.banking.customer.controller;

import com.carlos.banking.customer.dto.ClienteRequest;
import com.carlos.banking.customer.dto.ClienteResponse;
import com.carlos.banking.customer.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteResponse> obtenerCliente(
            @PathVariable Long clienteId
    ) {
        return ResponseEntity.ok(
                clienteService.obtenerCliente(clienteId)
        );
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(
            @Valid @RequestBody ClienteRequest request
    ) {
        ClienteResponse response = clienteService.crearCliente(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteResponse> actualizarCliente(
            @PathVariable Long clienteId,
            @Valid @RequestBody ClienteRequest request
    ) {
        return ResponseEntity.ok(
                clienteService.actualizarCliente(clienteId, request)
        );
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> eliminarCliente(
            @PathVariable Long clienteId
    ) {
        clienteService.eliminarCliente(clienteId);

        return ResponseEntity.noContent().build();
    }
}