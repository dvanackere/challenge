package com.challenge.client.service;

import com.challenge.client.integration.ClientResponse;
import com.challenge.client.model.Client;
import com.challenge.client.persistence.ClientsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}