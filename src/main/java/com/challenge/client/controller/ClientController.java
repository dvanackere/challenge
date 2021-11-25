package com.challenge.client.controller;

import com.challenge.client.integration.ClientResponse;
import com.challenge.client.request.CreateClientRequest;
import com.challenge.client.response.Kpi;
import com.challenge.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientService service;

    @GetMapping("/status")
    public String responderEstado() {
        return "OK";
    }

    @PostMapping("/crearCliente")
    public ResponseEntity createClient(@RequestBody CreateClientRequest request) {
        try {
            ClientResponse clientResponseCreated = service.createClient(request);
            return ResponseEntity.ok(clientResponseCreated);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listClientes")
    public ResponseEntity getAllClients() {
        List<ClientResponse> allClientsList = service.getAllClients();
        if (allClientsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(allClientsList);
    }

    @GetMapping("/kpiclientes")
    public ResponseEntity getKpiClients() {
        Optional<Kpi> kpi = service.calcularKPI();
        if (!kpi.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(kpi.get());
    }
}
