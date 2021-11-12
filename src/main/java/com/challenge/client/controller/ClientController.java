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

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientService service;

    @PostMapping("/crearCliente")
    public ResponseEntity createClient(@RequestBody CreateClientRequest request) {
        ClientResponse clientResponseCreated = service.createClient(request);
        return ResponseEntity.ok(clientResponseCreated);
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
        Kpi kpi = service.calcularKPI();
        return ResponseEntity.ok(kpi);
    }
}
