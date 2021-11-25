package com.challenge.client.service;

import com.challenge.client.integration.ClientResponse;
import com.challenge.client.model.Client;
import com.challenge.client.persistence.ClientsRepository;
import com.challenge.client.response.Kpi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ClientServiceTest {

    private ClientService service;
    @Mock
    private ClientsRepository repository;

    @BeforeEach
    public void setup() {
      service = new ClientService(repository);
    }

    @Test
    public void when_there_are_not_clients_in_database() {
        List<Client> client = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(client);

        List<ClientResponse> response = service.getAllClients();
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    public void when_listAllClients() {
        List<Client> clients = Arrays.asList(new Client(1,"Daniel","Perez", LocalDate.now()),
                new Client(2,"Juan","Perez", LocalDate.now()));
        Mockito.when(repository.findAll()).thenReturn(clients);

        List<ClientResponse> response = service.getAllClients();
        Assertions.assertTrue(response.size()==2);
    }

    @Test
    public void calculoKPI() {
        List<Client> clients = Arrays.asList(new Client(1,"Daniel","Perez", LocalDate.parse("1981-08-11", DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                new Client(2,"Juan","Perez",  LocalDate.parse("1980-06-20", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        Mockito.when(repository.findAll()).thenReturn(clients);

        Optional<Kpi> kpi = service.calcularKPI();

        Assertions.assertTrue(0.5==kpi.get().getDesviacionEstandar());
        Assertions.assertTrue(40.5==kpi.get().getMedia());
    }
}