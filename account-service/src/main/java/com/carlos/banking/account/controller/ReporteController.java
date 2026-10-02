package com.carlos.banking.account.controller;

import com.carlos.banking.account.dto.ReporteResponse;
import com.carlos.banking.account.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<ReporteResponse> generarReporte(
            @RequestParam Long clienteId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin
    ) {
        ReporteResponse response = reporteService.generarReporte(
                clienteId,
                fechaInicio,
                fechaFin
        );

        return ResponseEntity.ok(response);
    }
}