package com.upc.ep.ServicesIMPL;

import com.upc.ep.DTO.AsesorDTO;
import com.upc.ep.Entidad.Asesor;
import com.upc.ep.Repositorio.AsesorRepos;
import com.upc.ep.Services.AsesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AsesorIMPL implements AsesorService {
    @Autowired
    private AsesorRepos asesorRepos;

    @Override
    public Asesor saveAsesor(Asesor asesor) {
        return asesorRepos.save(asesor);
    }

    @Override
    public List<Asesor> listarA() {
        return asesorRepos.findAll();
    }

    @Override
    public AsesorDTO actualizarAsesor(Long id, AsesorDTO asesorDTO) {
        Asesor asesor = asesorRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Asesor no encontrado con ID: " + id));

        asesor.setNombreAsesor(asesorDTO.getNombreAsesor());
        asesor.setDniAsesor(asesorDTO.getDniAsesor());
        asesor.setTelefonoAsesor(asesorDTO.getTelefonoAsesor());
        asesor.setCorreoAsesor(asesorDTO.getCorreoAsesor());
        asesor.setFechaContratado(asesorDTO.getFechaContratado());

        Asesor actualizado = asesorRepos.save(asesor);

        AsesorDTO dtoActualizado = new AsesorDTO();
        dtoActualizado.setIdAsesor(actualizado.getIdAsesor());
        dtoActualizado.setNombreAsesor(actualizado.getNombreAsesor());
        dtoActualizado.setDniAsesor(actualizado.getDniAsesor());
        dtoActualizado.setTelefonoAsesor(actualizado.getTelefonoAsesor());
        dtoActualizado.setCorreoAsesor(actualizado.getCorreoAsesor());
        dtoActualizado.setFechaContratado(actualizado.getFechaContratado());

        return dtoActualizado;
    }
}
