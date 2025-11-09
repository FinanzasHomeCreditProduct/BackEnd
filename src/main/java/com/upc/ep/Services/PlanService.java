package com.upc.ep.Services;

import com.upc.ep.Entidad.Credito;
import com.upc.ep.Entidad.Plan;
import java.util.List;

public interface PlanService {
    List<Plan> generarPlan(Credito credito, Long numCuotas, Boolean periodoGracia);
    List<Plan> listarPlanPorCredito(Long idCredito);
}
