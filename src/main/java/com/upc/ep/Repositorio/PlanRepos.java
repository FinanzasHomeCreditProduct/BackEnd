package com.upc.ep.Repositorio;

import com.upc.ep.Entidad.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepos extends JpaRepository<Plan, Long> {
}
