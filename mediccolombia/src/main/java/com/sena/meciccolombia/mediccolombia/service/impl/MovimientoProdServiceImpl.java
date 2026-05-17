package com.sena.meciccolombia.mediccolombia.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.meciccolombia.mediccolombia.component.MovimientoProdMapper;
import com.sena.meciccolombia.mediccolombia.dao.MovimientoProdDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoMovimientoDAO;
import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.MovimientoProd;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.TipoMovimiento;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;
import com.sena.meciccolombia.mediccolombia.service.MovimientoProdService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.MovimientoProdRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoProdServiceImpl implements MovimientoProdService{

    private final MovimientoProdDAO movimientoProdDAO;
    private final ProductoDAO productoDAO;
    private final UsuarioDAO usuarioDAO;
    private final TipoMovimientoDAO tipoMovimientoDAO;
    private final MovimientoProdMapper movimientoProdMapper;
    @Override
    public MovimientoProdResponseDTO registrarMovimiento(MovimientoProdRequestDTO dto) {
    if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");

    Producto producto = productoDAO.findById(dto.getIdProducto())
                                            .orElseThrow(() -> new ResourceNotFoundException("El Producto con el ID:" + dto.getIdProducto() + " no fue encontrado o !existe"));
    Usuario usuario = usuarioDAO.findById(dto.getIdUsuario())
                                .orElseThrow(() -> new ResourceNotFoundException("El Usuario con el ID:" + dto.getIdUsuario() + " no fue encontrado o no existe"));
    TipoMovimiento tipoMovimiento = tipoMovimientoDAO.findById(dto.getIdTipoMovimiento())
                                .orElseThrow(() -> new ResourceNotFoundException("El TipoMovimiento con el ID:" + dto.getIdTipoMovimiento() + " no fue encontrado o no existe"));

    //Calcular el nuevo stock
    int nuevoStock = producto.getStock() + dto.getCantidad() * tipoMovimiento.getSigno();

    //Validacion del stock negativo
    if(nuevoStock < 0){
        throw new ResourceNotFoundException("Stok insuficiente. Stock actual:"
            + producto.getStock() + ", cantidad solicitada: " + dto.getCantidad());
    }

    //Colocar el nuevo stock
    producto.setStock(nuevoStock);
    productoDAO.save(producto);
    MovimientoProd movimientoProd = movimientoProdMapper.toEntity(dto, producto, tipoMovimiento, usuario);
    return movimientoProdMapper.toResponseDTO(movimientoProdDAO.save(movimientoProd));
    }

    @Override
    public MovimientoProdResponseDTO obtenerPorId(Long id) {
       if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");
       return movimientoProdDAO.findById(id)
                        .map(movimientoProdMapper::toResponseDTO)
                        .orElseThrow(() -> new ResourceNotFoundException("MovimientoProd con ID " + id + " no encontrado"));
    }

    @Override
    public List<MovimientoProdResponseDTO> listar() {
        return movimientoProdDAO.findAll()
                        .stream()
                        .map(movimientoProdMapper::toResponseDTO)
                        .toList();
    }

    @Override
    public List<MovimientoProdResponseDTO> listarPorProducto(Long idProducto) {
        if (idProducto == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        List<MovimientoProd> movimientoProd = movimientoProdDAO.findByProductoId(idProducto);
        return movimientoProd.stream()
                            .map(movimientoProdMapper::toResponseDTO)
                            .toList();
    }

    @Override
    public List<MovimientoProdResponseDTO> listarPorUsuario(Long idUsuario) {
        if (idUsuario == null) throw new IllegalArgumentException("El ID no puede ser nulo");
        return movimientoProdDAO.findByUsuarioId(idUsuario).stream()
                                                        .map(movimientoProdMapper::toResponseDTO)
                                                        .toList();
    }

    @Override
    public List<MovimientoProdResponseDTO> listarPorTipoMovimiento(Long idTipoMovimiento) {
     if (idTipoMovimiento == null) throw new IllegalArgumentException("El ID no puede ser nulo");
     List<MovimientoProd> movientos = movimientoProdDAO.findByTipoMovimientoId(idTipoMovimiento);
        return movientos.stream()
                            .map(movimientoProdMapper::toResponseDTO)
                            .toList();

                                                            
    }
    
}
