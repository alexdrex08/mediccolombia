package com.sena.meciccolombia.mediccolombia.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.meciccolombia.mediccolombia.dao.AlertaInvDAO;
import com.sena.meciccolombia.mediccolombia.dao.ProductoDAO;
import com.sena.meciccolombia.mediccolombia.domain.AlertaInv;
import com.sena.meciccolombia.mediccolombia.domain.Producto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertaInvScheduler {

    private final AlertaInvDAO alertaInvDAO;
    private final ProductoDAO productoDAO;

    private static final int DIAS_PROXIMOS_A_VENCER = 7;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void generarAlertasAutomaticas() {
        log.info("Iniciando generacion de alertas: {}", LocalDateTime.now());

        List<Producto> productos = productoDAO.findAll();

        for (Producto producto : productos) {
            verificarStockBajo(producto);
            verificarProximoAVencer(producto);
            verificarProductoVencido(producto);
        }
        log.info("Alertas generadas exitosamente");
    }

    private void verificarStockBajo(Producto producto) {
        if (producto.getStock() <= producto.getStockMinimo()
                && !alertaInvDAO.existsByProductoIdAndTipoAlerta(producto.getId(), "STOCK_BAJO")) {
            AlertaInv alerta = AlertaInv.builder()
                    .fechaCreacion(LocalDateTime.now())
                    .tipoAlerta("STOCK_BAJO")
                    .descripcion("El producto" + producto.getNombreProducto() +
                            " , tiene stock bajo. Stock actual:" + producto.getStock()
                            + ", stock minimo: " + producto.getStockMaximo())
                    .producto(producto)
                    .isResuelta(false)
                    .build();
            alertaInvDAO.save(alerta);
            log.info("Alerta STOCK_BAJO generada para: {}", producto.getNombreProducto());
        }
    }

    private void verificarProximoAVencer(Producto producto) {
        LocalDateTime limiteVencimiento = LocalDateTime.now().plusDays(DIAS_PROXIMOS_A_VENCER);

        if (producto.getFechaExpiracion() != null
                && producto.getFechaExpiracion().isBefore(limiteVencimiento)
                && producto.getFechaExpiracion().isAfter(LocalDateTime.now())
                && producto.getStock() > 0
                && !alertaInvDAO.existsByProductoIdAndTipoAlerta(producto.getId(), "PROXIMO_A_VENCER")) {
            AlertaInv alerta = AlertaInv.builder()
                    .fechaCreacion(LocalDateTime.now())
                    .tipoAlerta("PROXIMO_A_VENCER")
                    .descripcion("El producto: " + producto.getNombreProducto()
                            + " , vence el: " + producto.getFechaExpiracion()
                            + ". Stock actual: " + producto.getStock())
                    .producto(producto)
                    .isResuelta(false)
                    .build();
            alertaInvDAO.save(alerta);
            log.info("Alerta PROXIMO_A_VENCER generada para: {}", producto.getNombreProducto());
        }
    }
    private void verificarProductoVencido(Producto producto) {
        if (producto != null
            && producto.getFechaExpiracion().isBefore(LocalDateTime.now())
            && producto.getStock() > 0 
            && !alertaInvDAO.existsByProductoIdAndTipoAlerta(producto.getId(), "PRODUCTO_VENCIDO")
        ) 
        {
            AlertaInv alerta = AlertaInv.builder()
                .fechaCreacion(LocalDateTime.now())
                .tipoAlerta("PRODUCTO_VENCIDO")
                .descripcion("El producto "+ producto.getNombreProducto() 
                             + " , venció en la fecha: " + producto.getFechaExpiracion()
                             + ". Stock actual: " + producto.getStock())
                .producto(producto)
                .isResuelta(false)
                .build();

            alertaInvDAO.save(alerta);
            log.info("Alerta PRODUCTO_VENCIDO generada para: {}", producto.getNombreProducto());
        } 
    }
}
