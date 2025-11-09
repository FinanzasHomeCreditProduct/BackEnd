package com.upc.ep.Controller;

import com.upc.ep.Entidad.Plan;
import com.upc.ep.Services.PlanService;
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
public class PlanController {
    @Autowired
    private PlanService planService;

    // Listar las cuotas (plan de pagos) de un crédito
    @GetMapping("/plan/{idCredito}")
    @PreAuthorize("hasRole('ASESOR')")
    public ResponseEntity<List<Plan>> listarPorCredito(@PathVariable Long idCredito) {
        return ResponseEntity.ok(planService.listarPlanPorCredito(idCredito));
    }
}
