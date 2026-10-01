package com.carlos.banking.customer.mapper;

import com.carlos.banking.customer.dto.ClienteRequest;
import com.carlos.banking.customer.dto.ClienteResponse;
import com.carlos.banking.customer.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();

        cliente.setNombre(request.nombre());
        cliente.setGenero(request.genero());
        cliente.setEdad(request.edad());
        cliente.setIdentificacion(request.identificacion());
        cliente.setDireccion(request.direccion());
        cliente.setTelefono(request.telefono());

        cliente.setClienteId(request.clienteId());
        cliente.setContrasena(request.contrasena());
        cliente.setEstado(request.estado());

        return cliente;
    }

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getClienteId(),
                cliente.getNombre(),
                cliente.getGenero(),
                cliente.getEdad(),
                cliente.getIdentificacion(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getEstado()
        );
    }
}