package com.sena.meciccolombia.mediccolombia.service;

import java.util.List;
import com.sena.meciccolombia.mediccolombia.web.dto.request.DetalleFiltroRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.DetalleFiltroResponseDTO;

public interface DetalleFiltroService {

    DetalleFiltroResponseDTO crear(DetalleFiltroRequestDTO dto);
    DetalleFiltroResponseDTO actualizar(Long id, DetalleFiltroRequestDTO dto);
    void eliminar(Long id);
    DetalleFiltroResponseDTO obtenerPorId(Long id);
    List<DetalleFiltroResponseDTO> listarPorFiltro(Long idFiltroBusqueda);
}
