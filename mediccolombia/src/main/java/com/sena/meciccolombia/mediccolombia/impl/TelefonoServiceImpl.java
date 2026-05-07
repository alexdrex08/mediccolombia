package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.TelefonoMapper;
import com.sena.meciccolombia.mediccolombia.dao.ClienteDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProveedorDAO;
import com.sena.meciccolombia.mediccolombia.dao.TelefonoDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoTelefonoDAO;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.service.TelefonoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TelefonoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.TelefonoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TelefonoResponseDTO;

@Service
@RequiredArgsConstructor
public class TelefonoServiceImpl implements TelefonoService {

    private final TelefonoDAO telefonoDAO;
    private final TelefonoMapper telefonoMapper;
    private final TipoTelefonoDAO tipoTelefonoDAO;
    private final ClienteDAO clienteDAO;
    private final ProveedorDAO proveedorDAO;

    @Override
    @Transactional
    public TelefonoResponseDTO crear(TelefonoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
       
        if (dto.getIdCliente() == null && dto.getIdProveedor() == null){
            throw new IllegalArgumentException("El telefono debe pertenecer a un Cliente o un Proveedor");
        }

        if(dto.getIdCliente() != null && dto.getIdProveedor() != null){
            throw new IllegalArgumentException("El telefono solo puede pertecer a un Cliente o un Proveedor");
        }

        TipoTelefono tipoTelefono = tipoTelefonoDAO.findById(dto.getIdTipoTelefono())
                                .orElseThrow(() -> new RuntimeException ("Tipo telefono no econtrado"));

    Cliente cliente = null;
    Proveedor proveedor = null;

    if(dto.getIdCliente() != null){
        cliente  = clienteDAO.findById(dto.getIdCliente())
                        .orElseThrow( () -> new RuntimeException("Cliente no encontrado"));
    }
    if (dto.getIdProveedor() != null) {
        proveedor = proveedorDAO.findById(dto.getIdProveedor())
                        .orElseThrow( () -> new RuntimeException("Proveedor no encontrado"));
    }
    Telefono respuesta = telefonoMapper.toEntity(dto, tipoTelefono, cliente, proveedor);
    return telefonoMapper.toResponseDTO(telefonoDAO.save(respuesta));

    }

    @Override
    @Transactional(readOnly = true)
    public List<TelefonoResponseDTO> listar() {
        return telefonoDAO.findAll().stream()
                .map(telefonoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TelefonoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return telefonoDAO.findById(id)
                .map(telefonoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Telefono con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!telefonoDAO.existsById(id)) throw new RuntimeException("Telefono con ID " + id + " no encontrado");
        telefonoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public TelefonoResponseDTO actualizar(Long id, TelefonoUpdateRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

       Telefono telefono = telefonoDAO.findById(id) 
                            .orElseThrow( () -> new RuntimeException("Telefono no encontrado"));

        TipoTelefono tipoTelefono = tipoTelefonoDAO.findById(dto.getIdTipoTelefono())
                                    .orElseThrow(() -> new RuntimeException("Tipo de Telefono no encontrado"));

        telefono.setNumero(dto.getNumero());
        telefono.setComplemento(dto.getComplemento());
        telefono.setTipoTelefono(tipoTelefono);
        return telefonoMapper.toResponseDTO(telefonoDAO.save(telefono));
    }
}
