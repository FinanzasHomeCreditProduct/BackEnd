package com.upc.ep.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanDTO implements Serializable {
    private Long idPlan;
    private Long numCuota;
    private LocalDate fechaProgramada; //fecha programada para cada numero de cuota
    private Double saldoInicial;
    private Double montoCapital;
    private Double montoInteres;
    private Double pagoSeguro;
    private Double pagoTotal; //montoCapital + montoInteres + pagoSeguro
    private Double saldoDeuda; //saldo final
    private Double van;
    private Double tir;
    private Double tea;
    private Double tcea;
    private Boolean periodoGracia; //Cumple TRUE / No cumple False (sacado del credito)

    private CreditoDTO credito;
}
