package com.challenge.client.service;

import com.challenge.client.exception.ClientException;
import com.challenge.client.integration.ClientResponse;
import com.challenge.client.model.Client;
import com.challenge.client.persistence.ClientsRepository;
import com.challenge.client.request.CreateClientRequest;
import com.challenge.client.response.Kpi;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ClientService {
    @Autowired
    ClientsRepository repository;

    public ClientResponse createClient (CreateClientRequest request) throws ClientException {
        try {
            Client client = Client.builder()
                    .apellido(request.getApellido())
                    .nombre(request.getNombre())
                    .fechaNacimiento(request.getFechaNacimiento())
                    .build();
            log.info("Intentando crear el cliente {}",client);
            Client clientSaved = repository.save(client);
            ClientResponse response = ClientResponse.builder()
                    .id(clientSaved.getId())
                    .apellido(clientSaved.getApellido())
                    .nombre(clientSaved.getNombre())
                    .fechaNacimiento(clientSaved.getFechaNacimiento())
                    .edad(calcularEdad(clientSaved.getFechaNacimiento()))
                    .build();
            log.info("Se creo el cliente exitosamente");
            return response;
        } catch (Exception e) {
            log.error("Se lanzo la excepcion siguiente: {}",e.getMessage());
            throw new ClientException("Se lanzo la excepcion siguiente:"+ e.getMessage());
        }

    }

    public List<ClientResponse> getAllClients() {
        List<Client> clientList = repository.findAll();
        List<ClientResponse> clientResponseList = clientList.stream().map(client -> ClientResponse.builder()
                .id(client.getId())
                .apellido(client.getApellido())
                .nombre(client.getNombre())
                .fechaNacimiento(client.getFechaNacimiento())
                .edad(calcularEdad(client.getFechaNacimiento()))
                .fechaProbableMuerte(calcularFechaProbableMuerte(client.getFechaNacimiento()))
                .build()).collect(Collectors.toList());
        return clientResponseList;
    }

    public Optional<Kpi> calcularKPI() {
        double media;
        List<ClientResponse> allClients = getAllClients();
        if (allClients.isEmpty()) {
            return Optional.empty();
        }
        media = allClients.stream().mapToDouble(ClientResponse::getEdad).summaryStatistics().getAverage();
        double acumuladorDiferencias = 0;
        for (ClientResponse client : allClients) {
            acumuladorDiferencias = acumuladorDiferencias + ((client.getEdad() - media) * (client.getEdad() - media));
        }
        double varianza = acumuladorDiferencias / allClients.size();
        double desviacionEstandar = Math.sqrt(varianza);
        return Optional.of(Kpi.builder()
                .desviacionEstandar(desviacionEstandar)
                .media(media)
                .build());
    }

    private Integer calcularEdad(LocalDate fechaNac) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNac.toString(), format);
        LocalDate ahora = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, ahora);
        return edad.getYears();
    }

    private LocalDate calcularFechaProbableMuerte(LocalDate fechaNac) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNac.toString(), format);
        int numeroAniosVivir = (int) (Math.random()*100);
        LocalDate fechaMuerte = fechaNacimiento.plusYears(numeroAniosVivir);
        return fechaMuerte;
    }
}
