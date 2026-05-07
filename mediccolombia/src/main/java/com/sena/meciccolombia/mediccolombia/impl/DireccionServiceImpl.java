package com.sena.meciccolombia.mediccolombia.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.sena.meciccolombia.mediccolombia.component.DireccionMapper;
import com.sena.meciccolombia.mediccolombia.dao.BarrioDireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.ClienteDAO;
import com.sena.meciccolombia.mediccolombia.dao.DireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProveedorDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoDireccionDAO;
import com.sena.meciccolombia.mediccolombia.domain.*;
import com.sena.meciccolombia.mediccolombia.service.DireccionService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DireccionRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DireccionUpdateRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DireccionResponseDTO;

@Service
@RequiredArgsConstructor
public class DireccionServiceImpl implements DireccionService {

    private final DireccionDAO direccionDAO;
    private final DireccionMapper direccionMapper;
    private final ClienteDAO clienteDAO;
    private final ProveedorDAO proveedorDAO;
    private final TipoDireccionDAO tipoDireccionDAO;
    private final  BarrioDireccionDAO barrioDireccionDAO;

    @Override
    @Transactional
    public DireccionResponseDTO crear(DireccionRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        if(dto.getIdCliente() == null && dto.getIdProveedor() == null ){
            throw new IllegalArgumentException("La direccion debe pertener a un Cliente o un Proveedor");
        }
        if(dto.getIdCliente() != null && dto.getIdProveedor() != null) {
            throw new IllegalArgumentException("La direccion solo puede pertenecer a un Cliente o proveedor");
        }
        TipoDireccion tipoDireccion = tipoDireccionDAO.findById(dto.getIdTipoDireccion())
                                    .orElseThrow( () -> new RuntimeException("Tipo direccion no encontrada"));
        Cliente cliente = null;
        Proveedor proveedor = null;
        BarrioDireccion barrioDireccion = null;

        if(dto.getIdCliente() != null){
            cliente = clienteDAO.findById(dto.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        }
        if (dto.getIdProveedor() != null) {
            proveedor = proveedorDAO.findById(dto.getIdProveedor())
                        .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        }
        if(dto.getIdBarrio() != null) {
            barrioDireccion = barrioDireccionDAO.findById(dto.getIdBarrio())
                                .orElseThrow(() -> new RuntimeException("Barrio no encontrado"));
        }

        Direccion direccion = direccionMapper.toEntity(dto, barrioDireccion, cliente, proveedor, tipoDireccion);
        return direccionMapper.toResponseDTO(direccionDAO.save(direccion));
        
    }

    @Override
    @Transactional(readOnly = true)
    public List<DireccionResponseDTO> listar() {
        return direccionDAO.findAll().stream()
                .map(direccionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DireccionResponseDTO obtenerPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return direccionDAO.findById(id)
                .map(direccionMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Direccion con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (!direccionDAO.existsById(id)) throw new RuntimeException("Direccion con ID " + id + " no encontrado");
        direccionDAO.deleteById(id);
    }

    @Override
    @Transactional
    public DireccionResponseDTO actualizar(Long id, DireccionUpdateRequestDTO dto) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        
        Direccion direccion = direccionDAO.findById(id)
                                .orElseThrow( () -> new RuntimeException("Correo con ID:" + id + " no encontrado"));
        
        TipoDireccion tipoDireccion = tipoDireccionDAO.findById(dto.getIdTipoDireccion())
                                        .orElseThrow(() -> new RuntimeException("Tipo de direccion con ID" + dto.getIdTipoDireccion() + "no encontrada"));
        BarrioDireccion barrioDireccion = barrioDireccionDAO.findById(dto.getIdBarrioDireccion())
                                            .orElseThrow(() -> new RuntimeException("Barrio con el ID: "+ dto.getIdBarrioDireccion() + " no fue encontrado"));
        
        direccion.setDireccion(dto.getDireccion());
        direccion.setComplemento(dto.getComplemento());
        direccion.setTipoDireccion(tipoDireccion);
        direccion.setBarrio(barrioDireccion);

        return direccionMapper.toResponseDTO(direccionDAO.save(direccion));

    }
}
