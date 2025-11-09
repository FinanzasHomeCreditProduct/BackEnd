package com.upc.ep.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InmobiliariaDTO implements Serializable {
    private Long idInmobiliaria;

    private String nombreInmobiliaria;
    private String numeroUnidad; //Dpt. 501
    private String direccion; //Av. ----
    private String departamento; //Lima
    private String distrito; //Miraflores
    private String tipo; //departamento-casa-duplex
    private Double area2; //m2
    private String estado; //disponible-reservado-vendido
    private Double precio;

    private AsesorDTO asesor;
}
