package com.upc.ep.ServicesIMPL;

import com.upc.ep.DTO.ClienteDTO;
import com.upc.ep.Entidad.Cliente;
import com.upc.ep.Repositorio.ClienteRepos;
import com.upc.ep.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteIMPL implements ClienteService {
    @Autowired
    private ClienteRepos clienteRepos;

    @Override
    public Cliente saveCliente(Cliente cliente) {
        return clienteRepos.save(cliente);
    }

    @Override
    public List<Cliente> listarC() {
        return clienteRepos.findAll();
    }

    @Override
    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        cliente.setNombreCliente(clienteDTO.getNombreCliente());
        cliente.setDniCliente(clienteDTO.getDniCliente());
        cliente.setTelefonoCliente(clienteDTO.getTelefonoCliente());
        cliente.setCorreoCliente(clienteDTO.getCorreoCliente());
        cliente.setEstadoCivil(clienteDTO.getEstadoCivil());
        cliente.setDependientes(clienteDTO.getDependientes());
        cliente.setIngresoMensual(clienteDTO.getIngresoMensual());
        cliente.setOcupacion(clienteDTO.getOcupacion());

        Cliente actu = clienteRepos.save(cliente);

        ClienteDTO dtoActu = new ClienteDTO();
        dtoActu.setIdCliente(actu.getIdCliente());
        dtoActu.setNombreCliente(actu.getNombreCliente());
        dtoActu.setDniCliente(actu.getDniCliente());
        dtoActu.setTelefonoCliente(actu.getTelefonoCliente());
        dtoActu.setCorreoCliente(actu.getCorreoCliente());
        dtoActu.setEstadoCivil(actu.getEstadoCivil());
        dtoActu.setDependientes(actu.getDependientes());
        dtoActu.setIngresoMensual(actu.getIngresoMensual());
        dtoActu.setOcupacion(actu.getOcupacion());

        return dtoActu;
    }
}
