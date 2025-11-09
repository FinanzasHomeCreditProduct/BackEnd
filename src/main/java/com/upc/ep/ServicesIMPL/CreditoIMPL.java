package com.upc.ep.ServicesIMPL;

import com.upc.ep.Entidad.Credito;
import com.upc.ep.Entidad.Inmobiliaria;
import com.upc.ep.Repositorio.CreditoRepos;
import com.upc.ep.Repositorio.InmobiliariaRepos;
import com.upc.ep.Services.CreditoService;
import com.upc.ep.Services.PlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditoIMPL implements CreditoService {

    private final CreditoRepos creditoRepos;
    private final PlanService planService;
    private final InmobiliariaRepos  inmobiliariaRepos;

    @Override
    public Credito saveCredito(Credito credito) {

        // Validaciones mínimas (sin cambiar nombres)
        if (credito.getInmobiliaria() == null || credito.getInmobiliaria().getIdInmobiliaria() == null) {
            throw new IllegalArgumentException("Inmobiliaria requerida");
        }
        if (credito.getPlazo() == null || credito.getPlazo() <= 0) {
            throw new IllegalArgumentException("Plazo inválido");
        }
        if (credito.getCuotaInicial() < 0) {
            throw new IllegalArgumentException("Cuota inicial inválida");
        }

        // 1 Tomar precio de la inmobiliaria seleccionada
        Inmobiliaria inmobiliaria = inmobiliariaRepos.findById(credito.getInmobiliaria().getIdInmobiliaria())
                .orElseThrow(() -> new RuntimeException("Inmobiliaria no encontrada"));
        credito.setPrecioPropiedad(inmobiliaria.getPrecio());

        // ===== Cálculo preciso con BigDecimal (sin cambiar nombres de tus campos) =====
        BigDecimal precio = BigDecimal.valueOf(credito.getPrecioPropiedad());
        BigDecimal cuotaInicial = BigDecimal.valueOf(credito.getCuotaInicial());

        // Normaliza bonoTechoPropio:
        // - si viene 15 => 15%
        // - si viene 0.15 => 15%
        double rawBono = credito.getBonoTechoPropio();
        BigDecimal porcentajeBono = (rawBono > 1.0)
                ? BigDecimal.valueOf(rawBono)           // 15 -> 15%
                : BigDecimal.valueOf(rawBono * 100.0);  // 0.15 -> 15%

        BigDecimal bono = precio.multiply(porcentajeBono)
                .divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP); // precisión intermedia

        // 2 Calcular montoPrestamo = precio - cuotaInicial - bono (>= 0), redondeo a 2 decimales
        BigDecimal montoPrestamoBD = precio.subtract(cuotaInicial).subtract(bono);
        if (montoPrestamoBD.signum() < 0) montoPrestamoBD = BigDecimal.ZERO;
        montoPrestamoBD = montoPrestamoBD.setScale(2, RoundingMode.HALF_UP);
        credito.setMontoPrestamo(montoPrestamoBD.doubleValue());
        // ============================================================================

        // 3 Guardar crédito en BD
        Credito saved = creditoRepos.save(credito);

        // 4 Calcular número de cuotas (plazo en años → cuotas mensuales)
        Long numCuotas = saved.getPlazo() * 12L;

        // 5 Determinar si aplica periodo de gracia
        boolean periodoGracia = !"SinGracia".equalsIgnoreCase(saved.getPeriodo());

        // 6 Generar plan de pagos automáticamente
        planService.generarPlan(saved, numCuotas, periodoGracia);

        return saved;
    }

    @Override
    public List<Credito> listarCre() {
        return creditoRepos.findAll();
    }
}