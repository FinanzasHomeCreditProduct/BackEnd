package com.upc.ep.Repositorio;

import com.upc.ep.Entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepos extends JpaRepository<Cliente, Long> {
}
