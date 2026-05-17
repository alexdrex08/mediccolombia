package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.component.ClienteMapper;
import com.sena.meciccolombia.mediccolombia.component.CorreoMapper;
import com.sena.meciccolombia.mediccolombia.component.DireccionMapper;
import com.sena.meciccolombia.mediccolombia.component.TelefonoMapper;
import com.sena.meciccolombia.mediccolombia.dao.ClienteDAO;
import com.sena.meciccolombia.mediccolombia.dao.CorreoDAO;
import com.sena.meciccolombia.mediccolombia.dao.DireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.TelefonoDAO;
import com.sena.meciccolombia.mediccolombia.domain.Cliente;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.ClienteService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ClienteResquestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ClienteDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ClienteResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CorreoResponseClienteDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DireccionResponseClienteDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TelefonoResponseClienteDTO;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService{

    private final ClienteDAO clienteDAO;
    private final TelefonoDAO telefonoDAO;
    private final CorreoDAO correoDAO;
    private final DireccionDAO direccionDAO;

    private final ClienteMapper clienteMapper;
    private final TelefonoMapper telefonoMapper;
    private final CorreoMapper correoMapper;
    private final DireccionMapper direccionMapper;
    @Override
    @Transactional
    public ClienteResponseDTO crear(ClienteResquestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        if(clienteDAO.findByIdentificacion(dto.getIdentificacion()).isPresent()){
            throw new ResourceNotFoundException("Ya existe un cliente con esa Identificacion: " + dto.getIdentificacion());
        }

        Cliente cliente = clienteMapper.toEntity(dto);
        return clienteMapper.toResponseDTO(clienteDAO.save(cliente));
        
    }

    @Override
    @Transactional
    public ClienteResponseDTO actualizar(Long id, ClienteResquestDTO dto) {

        if(id == null) throw new IllegalArgumentException("El ID no puede ser Nulo");

        if(dto == null) throw new IllegalArgumentException("El DTO no puede ser Nulo");

        Cliente cliente = clienteDAO.findById(id)
                        .orElseThrow( () -> new RuntimeException("Cliente con ID" + id + " no encontrado"));
        cliente.setNombreCliente(dto.getNombreCliente());
        cliente.setIdentificacion(dto.getIdentificacion());

        return clienteMapper.toResponseDTO(clienteDAO.save(cliente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {

        if(id == null ) throw new IllegalArgumentException("el ID no puede ser nulo");
        if(!clienteDAO.existsById(id)) throw new ResourceNotFoundException("Cliente con ID" + id + " no encontrado");
        clienteDAO.deleteById(id);
    }
    

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("ID no puede ser vacio");
        return clienteDAO.findById(id)
                        .map(clienteMapper :: toResponseDTO)
                        .orElseThrow(() -> new RuntimeException("Cliente con ID" + id + " no encontrado"));
    }

    @Override
    @Transactional(readOnly =  true)
    public List<ClienteResponseDTO> listar() {

        return clienteDAO.findAll().stream()
                                    .map(clienteMapper::toResponseDTO)
                                    .toList();
    }

    @Override
    public ClienteResponseDTO buscarPorIdentificacion(String identificacion) {
        if (identificacion == null) throw new IllegalArgumentException("Identificacion no puede ser nula");
        return clienteDAO.findByIdentificacion(identificacion)
                        .map(clienteMapper::toResponseDTO)
                        .orElseThrow( () ->  new RuntimeException("Cliente con la identificacion: " + identificacion + " no fue encontrado"));
    }

    @Override
    public ClienteDetalleResponseDTO obtenerDetalles(Long id) {
     
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");

        Cliente cliente = clienteDAO.findById(id)
                                    .orElseThrow(() -> new RuntimeException("El cliente con el ID:" + id + " no fue encontrado o no existe"));
        
        List<TelefonoResponseClienteDTO> telefonos = telefonoDAO.findByClienteId(id)
                                        .stream().map(telefonoMapper::toResponseClienteDTO).toList();
        List<CorreoResponseClienteDTO> correos = correoDAO.findByClienteId(id)
                                        .stream().map(correoMapper::toResponseClienteDTO).toList();
        List<DireccionResponseClienteDTO> direcciones = direccionDAO.findByClienteId(id)
                                            .stream().map(direccionMapper::toResponseClienteDTO).toList();
        return ClienteDetalleResponseDTO.builder()
                                        .id(cliente.getId())
                                        .nombreCliente(cliente.getNombreCliente())
                                        .identificacion(cliente.getIdentificacion())
                                        .correos(correos)
                                        .direcciones(direcciones)
                                        .telefonos(telefonos)
                                        .build();
    }
    
}
