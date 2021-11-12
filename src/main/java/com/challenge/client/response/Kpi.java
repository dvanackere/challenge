package com.challenge.client.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Kpi {
    private double media;
    private double desviacionEstandar;
}
