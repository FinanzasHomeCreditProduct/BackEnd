package com.upc.ep.Services;

import com.upc.ep.DTO.ClienteDTO;
import com.upc.ep.Entidad.Cliente;
import java.util.List;

public interface ClienteService {
    public Cliente saveCliente(Cliente cliente);
    public List<Cliente> listarC();
    ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO);
}
