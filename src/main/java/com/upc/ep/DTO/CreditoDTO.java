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
public class CreditoDTO implements Serializable {
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

    private ClienteDTO cliente;

    private InmobiliariaDTO inmobiliaria;
}
