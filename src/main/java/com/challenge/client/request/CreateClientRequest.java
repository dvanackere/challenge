package com.challenge.client.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class CreateClientRequest {
    private String nombre;
    private String apellido;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;
}
