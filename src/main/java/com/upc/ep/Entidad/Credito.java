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
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCredito;

    private Double precioPropiedad;
    private Double cuotaInicial;
    private Double montoPrestamo;
    private Double bonoTechoPropio;
    private String monedaCredito; //Dolar-Soles
    private String tipoTasa; //Efectivo-Nominal
    private Double valorTasa; //En porcentaje
    private Long plazo; //años
    private String periodo; //SinGracia-GraciaTotal-GraciaParcial
    private Long mesesGracia;
    private Double tasaSeguro;
    private LocalDate fechaCreacion;


    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "inmobiliaria_id")
    private Inmobiliaria inmobiliaria;
}
