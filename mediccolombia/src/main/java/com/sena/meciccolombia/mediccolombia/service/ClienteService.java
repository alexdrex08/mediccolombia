package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;

import com.sena.meciccolombia.mediccolombia.web.dto.request.ClienteResquestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ClienteDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ClienteResponseDTO;

public interface ClienteService {

    ClienteResponseDTO crear(ClienteResquestDTO dto);
    ClienteResponseDTO actualizar(Long id, ClienteResquestDTO dto);
    void eliminar(Long id);

    ClienteResponseDTO obtenerPorId(Long id);

    List<ClienteResponseDTO> listar();

    ClienteResponseDTO buscarPorIdentificacion(String identificacion);

    ClienteDetalleResponseDTO obtenerDetalles(Long id);
}
