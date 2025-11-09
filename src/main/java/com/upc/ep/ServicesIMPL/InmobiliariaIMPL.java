package com.upc.ep.ServicesIMPL;

import com.upc.ep.DTO.InmobiliariaDTO;
import com.upc.ep.Entidad.Inmobiliaria;
import com.upc.ep.Repositorio.InmobiliariaRepos;
import com.upc.ep.Services.InmobiliariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InmobiliariaIMPL implements InmobiliariaService {
    @Autowired
    private InmobiliariaRepos inmobiliariaRepos;

    @Override
    public Inmobiliaria saveInmobiliaria(Inmobiliaria inmobiliaria) {
        return inmobiliariaRepos.save(inmobiliaria);
    }

    @Override
    public List<Inmobiliaria> listarI() {
        return inmobiliariaRepos.findAll();
    }

    @Override
    public InmobiliariaDTO actualizarInmobiliaria(Long id, InmobiliariaDTO inmobiliariaDTO) {
        Inmobiliaria inmobiliaria = inmobiliariaRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Inmobiliaria no encontrada con ID: " + id));

        inmobiliaria.setNombreInmobiliaria(inmobiliariaDTO.getNombreInmobiliaria());
        inmobiliaria.setNumeroUnidad(inmobiliariaDTO.getNumeroUnidad());
        inmobiliaria.setDireccion(inmobiliariaDTO.getDireccion());
        inmobiliaria.setDepartamento(inmobiliariaDTO.getDepartamento());
        inmobiliaria.setDistrito(inmobiliariaDTO.getDistrito());
        inmobiliaria.setTipo(inmobiliariaDTO.getTipo());
        inmobiliaria.setArea2(inmobiliariaDTO.getArea2());
        inmobiliaria.setEstado(inmobiliariaDTO.getEstado());
        inmobiliaria.setPrecio(inmobiliariaDTO.getPrecio());


        Inmobiliaria actualizadaI = inmobiliariaRepos.save(inmobiliaria);

        InmobiliariaDTO dtoI = new InmobiliariaDTO();
        dtoI.setIdInmobiliaria(actualizadaI.getIdInmobiliaria());
        dtoI.setNombreInmobiliaria(actualizadaI.getNombreInmobiliaria());
        dtoI.setNumeroUnidad(actualizadaI.getNumeroUnidad());
        dtoI.setDireccion(actualizadaI.getDireccion());
        dtoI.setDepartamento(actualizadaI.getDepartamento());
        dtoI.setDistrito(actualizadaI.getDistrito());
        dtoI.setTipo(actualizadaI.getTipo());
        dtoI.setArea2(actualizadaI.getArea2());
        dtoI.setEstado(actualizadaI.getEstado());
        dtoI.setPrecio(actualizadaI.getPrecio());

        return dtoI;
    }
}
