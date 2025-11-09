package com.upc.ep.Controller;

import com.upc.ep.DTO.InmobiliariaDTO;
import com.upc.ep.Entidad.Inmobiliaria;
import com.upc.ep.Services.InmobiliariaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/HomeCredit")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*"
)
public class InmobiliariaController {
    @Autowired
    private InmobiliariaService inmobiliariaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/inmobiliaria")
    @PreAuthorize("hasRole('ASESOR')")
    public InmobiliariaDTO saveInmobiliaria(@RequestBody InmobiliariaDTO inmobiliariaDTO) {
        Inmobiliaria inmobiliaria = modelMapper.map(inmobiliariaDTO, Inmobiliaria.class);
        inmobiliaria = inmobiliariaService.saveInmobiliaria(inmobiliaria);
        return modelMapper.map(inmobiliaria, InmobiliariaDTO.class);
    }

    @GetMapping("/inmobiliarias")
    @PreAuthorize("hasRole('ASESOR')")
    public List<InmobiliariaDTO> listarI() {
        List<Inmobiliaria> inmobiliarias = inmobiliariaService.listarI();
        ModelMapper modelMapper = new ModelMapper();
        return inmobiliarias.stream()
                .map(inmobiliaria -> modelMapper.map(inmobiliaria, InmobiliariaDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/inmobiliaria/modificar/{id}")
    @PreAuthorize("hasRole('ASESOR')")
    public ResponseEntity<InmobiliariaDTO> actualizarInmobiliaria(@PathVariable Long id, @RequestBody InmobiliariaDTO inmobiliariaDTO) {
        InmobiliariaDTO actualizadaI = inmobiliariaService.actualizarInmobiliaria(id, inmobiliariaDTO);
        return new ResponseEntity<>(actualizadaI, HttpStatus.OK);
    }
}
