package com.carlos.banking.customer.service;

import com.carlos.banking.customer.dto.ClienteRequest;
import com.carlos.banking.customer.dto.ClienteResponse;
import com.carlos.banking.customer.entity.Cliente;
import com.carlos.banking.customer.mapper.ClienteMapper;
import com.carlos.banking.customer.repository.ClienteRepository;
import com.carlos.banking.customer.exception.ClienteNotFoundException;
import com.carlos.banking.customer.exception.ClienteAlreadyExistsException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper
    ) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public List<ClienteResponse> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    public ClienteResponse obtenerCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException(
                        "Cliente no encontrado"
                ));

        return clienteMapper.toResponse(cliente);
    }

    public ClienteResponse crearCliente(ClienteRequest request) {

        if (clienteRepository.existsByClienteId(request.clienteId())) {
            throw new ClienteAlreadyExistsException(
                    "El clienteId ya existe"
            );
        }

        if (clienteRepository.existsByIdentificacion(request.identificacion())) {
            throw new ClienteAlreadyExistsException(
                    "La identificación ya existe"
            );
        }

        Cliente cliente = clienteMapper.toEntity(request);

        Cliente clienteGuardado = clienteRepository.save(cliente);

        return clienteMapper.toResponse(clienteGuardado);
    }

    public ClienteResponse actualizarCliente(
            Long clienteId,
            ClienteRequest request
    ) {
        Cliente cliente = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException(
                        "Cliente no encontrado"
                ));

        if (clienteRepository.existsByIdentificacionAndClienteIdNot(
                request.identificacion(),
                clienteId
        )) {
            throw new ClienteAlreadyExistsException(
                    "La identificación ya existe"
            );
        }

        cliente.setNombre(request.nombre());
        cliente.setGenero(request.genero());
        cliente.setEdad(request.edad());
        cliente.setIdentificacion(request.identificacion());
        cliente.setDireccion(request.direccion());
        cliente.setTelefono(request.telefono());
        cliente.setContrasena(request.contrasena());
        cliente.setEstado(request.estado());

        Cliente clienteActualizado = clienteRepository.save(cliente);

        return clienteMapper.toResponse(clienteActualizado);
    }

    public void eliminarCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException(
                        "Cliente no encontrado"
                ));

        clienteRepository.delete(cliente);
    }
}