package com.upc.ep.Entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Inmobiliaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInmobiliaria;

    private String nombreInmobiliaria;
    private String numeroUnidad; //Dpt. 501
    private String direccion; //Av. ---
    private String departamento; //Lima
    private String distrito; //Miraflores
    private String tipo; //departamento-casa-duplex
    private Double area2; //m2
    private String estado; //disponible-reservado-vendido
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "asesor_id")
    private Asesor asesor;
}
