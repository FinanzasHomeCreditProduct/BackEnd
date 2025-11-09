package com.upc.ep.Services;

import com.upc.ep.DTO.InmobiliariaDTO;
import com.upc.ep.Entidad.Inmobiliaria;
import java.util.List;

public interface InmobiliariaService {
    public Inmobiliaria saveInmobiliaria(Inmobiliaria inmobiliaria);
    public List<Inmobiliaria> listarI();
    InmobiliariaDTO actualizarInmobiliaria(Long id, InmobiliariaDTO inmobiliariaDTO);
}
