package com.sena.meciccolombia.mediccolombia.scheduler;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.dao.MovimientoProdDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.dao.TipoMovimientoDAO;
import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.MovimientoProd;
import com.sena.meciccolombia.mediccolombia.domain.Producto;
import com.sena.meciccolombia.mediccolombia.domain.TipoMovimiento;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;
import com.sena.meciccolombia.mediccolombia.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VencimientoScheduler {


    private final ProductoDAO productoDAO;
    private final MovimientoProdDAO movimientoProdDAO;
    private final TipoMovimientoDAO tipoMovimientoDAO;
    private final UsuarioDAO usuarioDAO;


    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void retirarProductosVencidos(){
        log.info("Iniciando tarea de retiro por vencimiento: {}", LocalDateTime.now());

        //buscar el tipoMovimiento de Retiro por vencimiento
        TipoMovimiento tipoMovimiento = tipoMovimientoDAO.findByNombreMovimiento("Retiro por Vencimiento")
                                                        .orElseThrow(() -> new ResourceNotFoundException("Tipo de movimiento 'Retiro por vencimiento' no encontrado"));
        //Ahora con los productos que se vencieron y que tiene un stock mayot a 0
        List<Producto> productosVencidos = productoDAO.findByFechaExpiracionBeforeAndStockGreaterThan(LocalDateTime.now(), 0);

        Usuario usuarioAdmin = usuarioDAO.findById(1L)
                                    .orElseThrow(() -> new ResourceNotFoundException("Usuario con el Id '1' no encontrado"));

        if(productosVencidos.isEmpty()){
            log.info("No hay productos vencidos para retirar");
            return;
        }
        for(Producto producto: productosVencidos) {
            MovimientoProd movimiento = MovimientoProd.builder()
                                                        .fechaMovimiento(LocalDateTime.now())
                                                        .cantidad(producto.getStock())
                                                        .movimiento("Retiro automático por vencimiento")
                                                        .tipoMovimiento(tipoMovimiento)
                                                        .producto(producto)
                                                        .usuario(usuarioAdmin)
                                                        .build();
        movimientoProdDAO.save(movimiento);
        producto.setStock(0);
        productoDAO.save(producto);

        log.info("Producto retirado por vencimiento: {} - Stock retirado: {}",
                producto.getNombreProducto(), movimiento.getCantidad()
        );

        }
        log.info("Tarea de reito por vencimiento finalizada. Productos procesados: {}",
            productosVencidos.size()
        );

    }
    
}
