package com.upc.ep.Controller;

import com.upc.ep.Entidad.Credito;
import com.upc.ep.Services.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HomeCredit")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*"
)
public class CreditoController {
    @Autowired
    private CreditoService creditoService;

    @PostMapping("/credito")
    @PreAuthorize("hasRole('ASESOR')")
    public ResponseEntity<Credito> registrarCredito(@RequestBody Credito credito) {
        Credito saved = creditoService.saveCredito(credito);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/creditos")
    @PreAuthorize("hasRole('ASESOR')")
    public ResponseEntity<List<Credito>> listarCreditos() {
        return ResponseEntity.ok(creditoService.listarCre());
    }
}
