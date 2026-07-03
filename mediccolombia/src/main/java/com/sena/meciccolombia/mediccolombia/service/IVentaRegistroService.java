package com.sena.meciccolombia.mediccolombia.service;

import java.time.LocalDateTime;
import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.VentaRegistroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ProductoMasVendidoResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TotalVendidosDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.VentaRegistroResponseDTO;

public interface IVentaRegistroService {

    VentaRegistroResponseDTO registrarVenta(VentaRegistroRequestDTO dto); 
    VentaRegistroResponseDTO obtenerVentaPorId(Long id);
    List<VentaRegistroResponseDTO> listarVentas();
    List<VentaRegistroResponseDTO> listarVentasPorCliente(Long idCliente);
    List<VentaRegistroResponseDTO> listarVentasPorUsuario(Long idUsuario);
    TotalVendidosDTO obtenerTotalVendidoEnElDia(LocalDateTime inico, LocalDateTime fin);
    List<ProductoMasVendidoResponseDTO> obtenerProductosMasVendidos();
    
}
