package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.meciccolombia.mediccolombia.component.AlertaInvMapper;
import com.sena.meciccolombia.mediccolombia.dao.AlertaInvDAO;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.AlertaInvService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.AlertaInvResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertaInvServiceImpl implements AlertaInvService{
    
    private final AlertaInvDAO alertaInvDAO;
    private final AlertaInvMapper alertaInvMapper;
    
    @Override
    public AlertaInvResponseDTO obtenerPorId(Long id) {
        if(id == null){
            throw new IllegalArgumentException("El ID de la Alerta no puede ser nulo");
        }
        return alertaInvDAO.findById(id)
            .map(alertaInvMapper::toResponseDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Alerta con el ID:" + id + " No fue encontrada"));
    }

    @Override
    public List<AlertaInvResponseDTO> listar() {
        return alertaInvDAO.findAll().stream()
        .map(alertaInvMapper::toResponseDTO)
        .toList();
    }

    @Override
    public List<AlertaInvResponseDTO> listarPorProducto(Long idProducto) {
        if(idProducto == null){
            throw new IllegalArgumentException("El ID de Producto no puede ser nulo");
        }
        return alertaInvDAO.findByProductoId(idProducto).stream()
                .map(alertaInvMapper::toResponseDTO)
                .toList();

    }

    @Override
    public List<AlertaInvResponseDTO> listarPorTipo(String tipoAlerta) {
        if(tipoAlerta == null){
            throw new IllegalArgumentException("El tipo de Alerta no puede ser nulo");
        }
        return alertaInvDAO.findByTipoAlerta(tipoAlerta).stream()
            .map(alertaInvMapper::toResponseDTO)
            .toList();

    }

    @Override
    public void eliminar(Long id) {
        if(id == null){
            throw new IllegalArgumentException("El ID de categoría no puede ser nulo");
        }
        if(!alertaInvDAO.existsById(id)) throw new ResourceNotFoundException("Alerta con el ID: "+ id + " no encontrada");
        alertaInvDAO.deleteById(id);
    }
    
}
