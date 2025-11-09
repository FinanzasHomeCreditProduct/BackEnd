package com.upc.ep.Repositorio;

import com.upc.ep.Entidad.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditoRepos extends JpaRepository<Credito, Long> {
}
