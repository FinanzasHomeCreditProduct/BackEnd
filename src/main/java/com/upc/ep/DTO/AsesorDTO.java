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
public class AsesorDTO implements Serializable {
    private Long idAsesor;

    private String nombreAsesor;
    private String dniAsesor;
    private String telefonoAsesor;
    private String correoAsesor;
    private LocalDate fechaContratado;
}
