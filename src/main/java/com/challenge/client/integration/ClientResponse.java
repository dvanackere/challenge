package com.challenge.client.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponse {
    private Integer id;

    private String nombre;
    private String apellido;
    private Integer edad;
    private LocalDate fechaNacimiento;
    private LocalDate fechaProbableMuerte;

    public Integer calcularEdad(LocalDate fechaNac) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNac.toString(), format);
        LocalDate ahora = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, ahora);
        return edad.getYears();
    }

    public LocalDate calcularFechaProbableMuerte(LocalDate fechaNac) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNac.toString(), format);
        LocalDate fechaMuerte = fechaNacimiento.plusYears(30);
        return fechaMuerte;
    }
}
