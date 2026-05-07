package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.CorreoMapper;
import com.sena.meciccolombia.mediccolombia.dao.ClienteDAO;
import com.sena.meciccolombia.mediccolombia.dao.CorreoDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProveedorDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoCorreoDAO;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.service.CorreoService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CorreoRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.CorreoUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CorreoResponseDTO;

@Service
@RequiredArgsConstructor
public class CorreoServiceImpl implements CorreoService {

    private final CorreoDAO correoDAO;
    private final CorreoMapper correoMapper;
    private final ClienteDAO clienteDAO;
    private final ProveedorDAO proveedorDAO;
    private final TipoCorreoDAO tipoCorreoDAO;

    @Override
    @Transactional
    public CorreoResponseDTO crear(CorreoRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        
        if(dto.getIdCliente() == null && dto.getIdProveedor() == null){
            throw new IllegalArgumentException("El correo debe pertener o a un cliente o un proveedor");
        }
        if(dto.getIdCliente() != null && dto.getIdProveedor() != null){
            throw new IllegalArgumentException("El correo solo puede pertenecer a un cliente o proveedor a la vez");
        }
        TipoCorreo tipoCorreo = tipoCorreoDAO.findById(dto.getIdTipoCorreo())
                .orElseThrow( () -> new RuntimeException("TipoCorreo no encontrado"));  

        Cliente cliente = null;
        Proveedor proveedor = null;

        if (dto.getIdCliente() != null){
            cliente = clienteDAO.findById(dto.getIdCliente())
                        .orElseThrow( () -> new RuntimeException("Cliente no encontrado"));
        }
        if(dto.getIdProveedor() != null){
            proveedor = proveedorDAO.findById(dto.getIdProveedor())
                        .orElseThrow( () -> new RuntimeException("Proveedor no encontrado"));
        }
       Correo correo = correoMapper.toEntity(dto, tipoCorreo, cliente, proveedor);
        return correoMapper.toResponseDTO(correoDAO.save(correo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CorreoResponseDTO> listar() {
        return correoDAO.findAll().stream()
                .map(correoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CorreoResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return correoDAO.findById(id)
                .map(correoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Correo con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!correoDAO.existsById(id)) throw new RuntimeException("Correo con ID " + id + " no encontrado");
        correoDAO.deleteById(id);
    }

    @Override
    @Transactional
    public CorreoResponseDTO actualizar(Long id, CorreoUpdateRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        Correo correo = correoDAO.findById(id)
                        .orElseThrow(() -> new RuntimeException("Correo con ID " + id +" no encontrado"));

        TipoCorreo tipoCorreo = tipoCorreoDAO.findById(dto.getIdTipoCorreo())
                                .orElseThrow(() -> new RuntimeException("TipoCorreo con ID" + dto.getIdTipoCorreo() + " no encontrado"));
        
        correo.setCorreoElectronico(dto.getCorreoElectronico());
        correo.setTipoCorreo(tipoCorreo);

        
        return correoMapper.toResponseDTO(correoDAO.save(correo));
    }
}
