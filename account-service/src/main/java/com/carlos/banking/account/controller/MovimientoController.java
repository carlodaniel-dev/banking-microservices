package com.carlos.banking.account.controller;

import com.carlos.banking.account.dto.MovimientoRequest;
import com.carlos.banking.account.dto.MovimientoResponse;
import com.carlos.banking.account.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<MovimientoResponse> registrarMovimiento(
            @Valid @RequestBody MovimientoRequest request
    ) {
        MovimientoResponse response =
                movimientoService.registrarMovimiento(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}