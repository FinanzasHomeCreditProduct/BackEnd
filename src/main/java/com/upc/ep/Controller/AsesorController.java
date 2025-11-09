package com.upc.ep.Controller;

import com.upc.ep.DTO.AsesorDTO;
import com.upc.ep.Entidad.Asesor;
import com.upc.ep.Services.AsesorService;
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
public class AsesorController {
    @Autowired
    private AsesorService asesorService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/asesor")
    @PreAuthorize("hasRole('ASESOR')")
    public AsesorDTO saveAsesor(@RequestBody AsesorDTO asesorDTO) {
        Asesor asesor = modelMapper.map(asesorDTO, Asesor.class);
        asesor = asesorService.saveAsesor(asesor);
        return modelMapper.map(asesor, AsesorDTO.class);
    }

    @GetMapping("/asesores")
    @PreAuthorize("hasRole('ASESOR')")
    public List<AsesorDTO> listarA() {
        List<Asesor> asesores = asesorService.listarA();
        ModelMapper modelMapper = new ModelMapper();
        return asesores.stream()
                .map(asesor -> modelMapper.map(asesor, AsesorDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/asesor/modificar/{id}")
    @PreAuthorize("hasRole('ASESOR')")
    public ResponseEntity<AsesorDTO> actualizarAsesor(@PathVariable Long id, @RequestBody AsesorDTO asesorDTO) {
        AsesorDTO actualizado = asesorService.actualizarAsesor(id, asesorDTO);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }
}
