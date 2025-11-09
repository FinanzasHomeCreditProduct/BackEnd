package com.upc.ep.Entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;

    private Long numCuota;
    private LocalDate fechaProgramada;
    private Double saldoInicial;
    private Double montoCapital;
    private Double montoInteres;
    private Double pagoSeguro;
    private Double pagoTotal;// montoCapital + montoInteres + pagoSeguro
    private Double saldoDeuda;
    private Double van;
    private Double tir;
    private Double tea;
    private Double tcea;
    private Boolean periodoGracia; //Cumple TRUE / No cumple False

    @ManyToOne
    @JoinColumn(name = "credito_id")
    private Credito credito;
}
