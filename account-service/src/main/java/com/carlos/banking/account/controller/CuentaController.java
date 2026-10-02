package com.carlos.banking.account.controller;

import com.carlos.banking.account.dto.CuentaRequest;
import com.carlos.banking.account.dto.CuentaResponse;
import com.carlos.banking.account.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> listarCuentas() {
        return ResponseEntity.ok(
                cuentaService.listarCuentas()
        );
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaResponse> obtenerCuenta(
            @PathVariable Long numeroCuenta
    ) {
        return ResponseEntity.ok(
                cuentaService.obtenerCuenta(numeroCuenta)
        );
    }

    @PostMapping
    public ResponseEntity<CuentaResponse> crearCuenta(
            @Valid @RequestBody CuentaRequest request
    ) {
        CuentaResponse response = cuentaService.crearCuenta(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaResponse> actualizarCuenta(
            @PathVariable Long numeroCuenta,
            @Valid @RequestBody CuentaRequest request
    ) {
        return ResponseEntity.ok(
                cuentaService.actualizarCuenta(
                        numeroCuenta,
                        request
                )
        );
    }

    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<Void> eliminarCuenta(
            @PathVariable Long numeroCuenta
    ) {
        cuentaService.eliminarCuenta(numeroCuenta);

        return ResponseEntity.noContent().build();
    }
}