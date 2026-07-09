package com.sena.meciccolombia.mediccolombia.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.component.AlertaInvMapper;
import com.sena.meciccolombia.mediccolombia.dao.AlertaInvDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.domain.AlertaInv;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.AlertaInvService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.AlertaInvResponseDTO;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertaInvServiceImpl implements AlertaInvService {

    private final AlertaInvDAO alertaInvDAO;
    private final ProductoDAO productoDAO;
    private final AlertaInvMapper alertaInvMapper;

    @Override
    public AlertaInvResponseDTO obtenerPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la Alerta no puede ser nulo");
        }
        return alertaInvDAO.findById(id)
                .map(alertaInvMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta con el ID:" + id + " No fue encontrada"));
    }

    @Override
    public List<AlertaInvResponseDTO> listarIsResueltaFalse() {
        return alertaInvDAO.findByIsResueltaFalse().stream()
                .map(alertaInvMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<AlertaInvResponseDTO> listarIsResueltaTrue() {
        return alertaInvDAO.findByIsResueltaTrue().stream()
                .map(alertaInvMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<AlertaInvResponseDTO> listarPorProducto(Long idProducto) {
        if (idProducto == null) {
            throw new IllegalArgumentException("El ID de Producto no puede ser nulo");
        }
        return alertaInvDAO.findByProductoId(idProducto).stream()
                .map(alertaInvMapper::toResponseDTO)
                .toList();

    }

    @Override
    public List<AlertaInvResponseDTO> listarPorTipoYEstado(String tipoAlerta, Boolean isResuelta) {
        if (tipoAlerta == null) {
            throw new IllegalArgumentException("El tipo de Alerta no puede ser nulo");
        }
        return alertaInvDAO.findByTipoAlertaAndIsResuelta(tipoAlerta, isResuelta).stream()
                .map(alertaInvMapper::toResponseDTO)
                .toList();

    }

    @Override
    @Transactional
    public void resolverAlerta(Long id) {

        AlertaInv alerta = alertaInvDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la alerta"));

        switch (alerta.getTipoAlerta()) {

            case "PRODUCTO_VENCIDO":
                resolverProductoVencido(alerta);
                break;

            case "PROXIMO_A_VENCER":
                resolverProximoAVencer(alerta);
                break;

            case "STOCK_BAJO":
                resolverStockBajo(alerta);
                break;
        }
    }

    private void resolverProductoVencido(AlertaInv alerta) {
        Producto producto = alerta.getProducto();
        producto.setStock(0);
        productoDAO.save(producto);

        alerta.setIsResuelta(true);
        alerta.setFechaResolucion(LocalDateTime.now());
        alertaInvDAO.save(alerta);
    }

    private void resolverProximoAVencer(AlertaInv alerta) {
        // No soportado por el momento

        alerta.setIsResuelta(true);
        alerta.setFechaResolucion(LocalDateTime.now());
        alertaInvDAO.save(alerta);
    }

    private void resolverStockBajo(AlertaInv alerta) {
        Producto producto = alerta.getProducto();

        if (producto.getStock() <= producto.getStockMinimo()) {
            throw new IllegalStateException(
                    "El producto continúa con stock bajo.");
        }
        alerta.setIsResuelta(true);
        alerta.setFechaResolucion(LocalDateTime.now());
        alertaInvDAO.save(alerta);

    }

}
