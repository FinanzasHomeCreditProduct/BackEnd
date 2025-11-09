package com.upc.ep.ServicesIMPL;

import com.upc.ep.Entidad.Credito;
import com.upc.ep.Entidad.Plan;
import com.upc.ep.Repositorio.PlanRepos;
import com.upc.ep.Services.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanIMPL implements PlanService {
    private final PlanRepos planRepos;

    @Override
    public List<Plan> generarPlan(Credito credito, Long numCuotas, Boolean periodoGracia) {
        List<Plan> planList = new ArrayList<>();

        double saldo = credito.getMontoPrestamo();
        double tasaAnual = credito.getValorTasa() / 100.0;

        // Calcular tasa mensual según tipo de tasa
        double tasaMensual;
        if ("Efectiva".equalsIgnoreCase(credito.getTipoTasa())) {
            tasaMensual = Math.pow(1 + tasaAnual, 1.0 / 12) - 1;
        } else { // Nominal (asumimos capitalización mensual)
            tasaMensual = tasaAnual / 12;
        }

        // Calcular cuota fija (sistema francés)
        double cuotaFija = (saldo * tasaMensual) / (1 - Math.pow(1 + tasaMensual, -numCuotas));

        // Para guardar los flujos de caja
        double[] flujos = new double[numCuotas.intValue() + 1];
        flujos[0] = -saldo; // desembolso inicial negativo

        for (int i = 1; i <= numCuotas; i++) {
            Plan plan = new Plan();
            plan.setCredito(credito);
            plan.setNumCuota((long) i);
            plan.setSaldoInicial(saldo);

            double montoInteres = saldo * tasaMensual;
            double montoCapital = cuotaFija - montoInteres;
            double pagoSeguro = saldo * credito.getTasaSeguro();
            double pagoTotal = montoCapital + montoInteres + pagoSeguro;

            saldo -= montoCapital;

            plan.setMontoCapital(montoCapital);
            plan.setMontoInteres(montoInteres);
            plan.setPagoSeguro(pagoSeguro);
            plan.setPagoTotal(pagoTotal);
            plan.setSaldoDeuda(saldo);

            // Guardar en flujos para VAN/TIR
            flujos[i] = pagoTotal;

            // Marcar periodo de gracia según meses de gracia
            if (periodoGracia && credito.getMesesGracia() != null) {
                plan.setPeriodoGracia(i <= credito.getMesesGracia());
            } else {
                plan.setPeriodoGracia(false);
            }

            // Fecha programada de la cuota (cada mes)
            plan.setFechaProgramada(LocalDate.now().plusMonths(i));

            planRepos.save(plan);
            planList.add(plan);
        }

        // === Calcular indicadores financieros ===
        double van = calcularVAN(tasaMensual, flujos);
        double tir = calcularTIR(flujos);
        double tea = Math.pow(1 + tasaMensual, 12) - 1;
        double tcea = Math.pow(1 + (tir / 12), 12) - 1;

        // Asignar a cada plan
        for (Plan plan : planList) {
            plan.setVan(van);
            plan.setTir(tir);
            plan.setTea(tea);
            plan.setTcea(tcea);
        }

        return planList;
    }

    @Override
    public List<Plan> listarPlanPorCredito(Long idCredito) {
        return planRepos.findAll().stream()
                .filter(p -> p.getCredito().getIdCredito().equals(idCredito))
                .collect(Collectors.toList());
    }

    // --- Métodos auxiliares ---

    private double calcularVAN(double tasa, double[] flujos) {
        double van = 0;
        for (int i = 0; i < flujos.length; i++) {
            van += flujos[i] / Math.pow(1 + tasa, i);
        }
        return van;
    }

    private double calcularTIR(double[] flujos) {
        double tir = 0.1; // Valor inicial 10%
        for (int iter = 0; iter < 100; iter++) {
            double f = 0, fDeriv = 0;
            for (int i = 0; i < flujos.length; i++) {
                f += flujos[i] / Math.pow(1 + tir, i);
                if (i > 0) {
                    fDeriv += -i * flujos[i] / Math.pow(1 + tir, i + 1);
                }
            }
            double tirNuevo = tir - f / fDeriv;
            if (Math.abs(tirNuevo - tir) < 1e-7) return tirNuevo;
            tir = tirNuevo;
        }
        return tir;
    }
}
