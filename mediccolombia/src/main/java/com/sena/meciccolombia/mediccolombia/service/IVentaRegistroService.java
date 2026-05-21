package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.VentaRegistroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.VentaRegistroResponseDTO;

public interface IVentaRegistroService {

    VentaRegistroResponseDTO registrarVenta(VentaRegistroRequestDTO dto); 
    VentaRegistroResponseDTO obtenerVentaPorId(Long id);
    List<VentaRegistroResponseDTO> listarVentas();
    List<VentaRegistroResponseDTO> listarVentasPorCliente(Long idCliente);
    List<VentaRegistroResponseDTO> listarVentasPorUsuario(Long idUsuario);
    
}
