package com.upc.ep.Entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nombreCliente;
    private String dniCliente; //DNI - Carnet - Pasaporte
    private String telefonoCliente;
    private String correoCliente;
    private String estadoCivil; //Soltero-Casado-Divorciado-Viudo
    private Long dependientes; //Cantidad de personas q dependen economicamente del cliente
    private Double ingresoMensual;
    private String ocupacion; //Profesion
}
