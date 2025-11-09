package com.upc.ep.Services;

import com.upc.ep.Entidad.Credito;
import java.util.List;

public interface CreditoService {
    Credito saveCredito(Credito credito); // Registra un crédito y genera plan
    List<Credito> listarCre();            // Lista todos los créditos
}
