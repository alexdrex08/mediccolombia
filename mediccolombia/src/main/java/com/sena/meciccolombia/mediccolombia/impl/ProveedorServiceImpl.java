package com.sena.meciccolombia.mediccolombia.impl;

import com.sena.meciccolombia.mediccolombia.component.CorreoMapper;
import com.sena.meciccolombia.mediccolombia.component.DireccionMapper;
import com.sena.meciccolombia.mediccolombia.component.ProveedorMapper;
import com.sena.meciccolombia.mediccolombia.component.TelefonoMapper;
import com.sena.meciccolombia.mediccolombia.dao.CorreoDAO;
import com.sena.meciccolombia.mediccolombia.dao.DireccionDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProveedorDAO;
import com.sena.meciccolombia.mediccolombia.dao.TelefonoDAO;
import com.sena.meciccolombia.mediccolombia.domain.Proveedor;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.meciccolombia.mediccolombia.service.ProveedorService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ProveedorRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.CorreoResponseProveedorDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DireccionResponseProveedorDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProveedorDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProveedorResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TelefonoResponseProveedorDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService{
    

    private final ProveedorDAO proveedorDAO;
    private final TelefonoDAO telefonoDAO;
    private final CorreoDAO correoDAO;
    private final DireccionDAO direccionDAO;

    private final ProveedorMapper proveedorMapper;
     private final TelefonoMapper telefonoMapper;
    private final CorreoMapper correoMapper;
    private final DireccionMapper direccionMapper;

@Override
    public ProveedorResponseDTO crear(ProveedorRequestDTO dto) {

        if(dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

        if(proveedorDAO.findByNit(dto.getNit()).isPresent()){
            throw new ResourceNotFoundException("El proveedor con el NIT: "+ dto.getNit() + " ya existe");
        }

        Proveedor proveedor = proveedorMapper.toEntity(dto);
        return proveedorMapper.toResponseDTO(proveedorDAO.save(proveedor));
    }

    @Override
    public ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto) {
        if(dto == null) throw new IllegalArgumentException("El DTO no puedeo ser nulo");

        if(id == null) throw new IllegalArgumentException("El ID no puede ser nulo");

        Proveedor proveedor = proveedorDAO.findById(id)
                                            .orElseThrow(() -> new RuntimeException("El proveedor con el ID" + id + "no fue encontrado"));
        
        proveedor.setNombreProv(dto.getNombreProv());
        proveedor.setNit(dto.getNit());

        return proveedorMapper.toResponseDTO(proveedorDAO.save(proveedor));
    }

    @Override
    public void eliminar(Long id) {
        if(id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        if(!proveedorDAO.existsById(id)) throw new ResourceNotFoundException("El cliente con ID: "+ id+ " no fue encontrado");
        proveedorDAO.deleteById(id);
     }

    @Override
    public ProveedorResponseDTO buscarPorId(Long id) {
        if(id == null ) throw new IllegalArgumentException("El ID no puede ser nulo");
        return proveedorDAO.findById(id)
                            .map(proveedorMapper::toResponseDTO)
                            .orElseThrow(() -> new RuntimeException("El cliente con el ID:" + id + " no fue encontrado"));
    }

    @Override
    public List<ProveedorResponseDTO> listar() {
        return proveedorDAO.findAll().stream()
                                    .map(proveedorMapper::toResponseDTO)
                                    .toList();
    }

    @Override
    public ProveedorResponseDTO buscarPorNit(String nit) {
       if (nit  == null ) throw new IllegalArgumentException("El nit no puede ser nulo"); 
       return proveedorDAO.findByNit(nit)
                        .map(proveedorMapper::toResponseDTO)
                        .orElseThrow(() -> new RuntimeException("El nit: " + nit + " no fue encontrado"));
    }

    @Override
    public ProveedorDetalleResponseDTO obtenerDetalles(Long id) {
      if (id == null)throw new IllegalArgumentException("El ID no puede ser nulo");

      Proveedor proveedor = proveedorDAO.findById(id)
                                        .orElseThrow( () -> new RuntimeException("El proveedor con el ID: " + id + " no existe o no fue encontrado"));
        List<CorreoResponseProveedorDTO> correos = correoDAO.findByProveedorId(id)
                                        .stream().map(correoMapper::toResponseProveedorDTO).toList();
        List<DireccionResponseProveedorDTO> direcciones = direccionDAO.findByProveedorId(id)
                                            .stream().map(direccionMapper::toResponseProveedorDTO).toList();
        List<TelefonoResponseProveedorDTO> telefonos = telefonoDAO.findByProveedorId(id)
                                            .stream().map(telefonoMapper::toResponseProveedorDTO).toList();
        
        return ProveedorDetalleResponseDTO.builder()
                            .id(proveedor.getId())
                            .nombreProv(proveedor.getNombreProv())
                            .nit(proveedor.getNit())
                            .correos(correos)
                            .direcciones(direcciones)
                            .telefonos(telefonos)
                            .build();
    }
    
}
