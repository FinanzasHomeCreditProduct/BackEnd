package com.upc.ep.Services;

import com.upc.ep.DTO.AsesorDTO;
import com.upc.ep.Entidad.Asesor;
import java.util.List;

public interface AsesorService {
    public Asesor saveAsesor(Asesor asesor);
    public List<Asesor> listarA();
    AsesorDTO actualizarAsesor(Long id, AsesorDTO asesorDTO);
}
