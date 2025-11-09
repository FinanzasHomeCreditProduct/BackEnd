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
public class ClienteDTO implements Serializable {
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
