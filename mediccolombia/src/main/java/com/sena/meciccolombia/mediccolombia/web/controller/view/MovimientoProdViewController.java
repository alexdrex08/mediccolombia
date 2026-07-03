package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.meciccolombia.mediccolombia.service.MovimientoProdService;
import com.sena.meciccolombia.mediccolombia.web.dto.response.MovimientoProdResponseDTO;

@Controller
@RequestMapping("/movimientos")
public class MovimientoProdViewController {

    private final MovimientoProdService movimientoProdService;

    public MovimientoProdViewController(MovimientoProdService movimientoProdService) {
        this.movimientoProdService = movimientoProdService;
    }

    @GetMapping("/entradas")
    public String listarEntradas(Model model) {
        List<MovimientoProdResponseDTO> entradas = movimientoProdService.listarPorSigno(1);

        model.addAttribute("movimientos", entradas);
        model.addAttribute("vistaActiva", "movimientos");
        model.addAttribute("titulo", "Entradas de Inventario");
        model.addAttribute("filtroActivo", "entradas");

        model.addAttribute("totalEntradas", entradas.size());
        model.addAttribute("ultimaEntrada", obtenerFechaMasReciente(entradas));
        model.addAttribute("productoMasIngresado", obtenerMasFrecuentePorCantidad(entradas, MovimientoProdResponseDTO::getNombreProducto));
        model.addAttribute("tipoMasFrecuente", obtenerMasFrecuentePorCantidad(entradas, MovimientoProdResponseDTO::getTipoMovimiento));

        return "movimiento/movimientos-entradas";
    }

    @GetMapping("/salidas")
    public String listarSalidas(Model model) {
        List<MovimientoProdResponseDTO> salidas = movimientoProdService.listarPorSigno(-1);

        model.addAttribute("movimientos", salidas);
        model.addAttribute("vistaActiva", "movimientos");
        model.addAttribute("titulo", "Salidas de Inventario");
        model.addAttribute("filtroActivo", "salidas");

        model.addAttribute("totalSalidas", salidas.size());
        model.addAttribute("ultimaSalida", obtenerFechaMasReciente(salidas));
        model.addAttribute("productoMasDespachado", obtenerMasFrecuentePorCantidad(salidas, MovimientoProdResponseDTO::getNombreProducto));
        model.addAttribute("responsableMasActivo", obtenerUsuarioMasFrecuente(salidas));

        return "movimiento/movimientos-salidas";
    }

    @GetMapping("/ver-entrada/{id}")
    public String verDetalleEntrada(@PathVariable Long id, Model model) {
        model.addAttribute("movimiento", movimientoProdService.obtenerPorId(id));
        model.addAttribute("vistaActiva", "movimientos");
        return "movimiento/detalle-movimiento-entrada";
    }

    @GetMapping("/ver-salida/{id}")
    public String verDetalleSalida(@PathVariable Long id, Model model) {
        model.addAttribute("movimiento", movimientoProdService.obtenerPorId(id));
        model.addAttribute("vistaActiva", "movimientos");
        return "movimiento/detalle-movimiento-salida";
    }

    private String obtenerFechaMasReciente(List<MovimientoProdResponseDTO> movimientos) {
        return movimientos.stream()
                .map(MovimientoProdResponseDTO::getFechaMovimiento)
                .max(Comparator.naturalOrder())
                .map(fecha -> fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .orElse("Sin registros");
    }

    private String obtenerMasFrecuentePorCantidad(
            List<MovimientoProdResponseDTO> movimientos,
            java.util.function.Function<MovimientoProdResponseDTO, String> clave) {

        return movimientos.stream()
                .collect(Collectors.groupingBy(clave, Collectors.summingInt(MovimientoProdResponseDTO::getCantidad)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Sin registros");
    }

    private String obtenerUsuarioMasFrecuente(List<MovimientoProdResponseDTO> movimientos) {
        return movimientos.stream()
                .collect(Collectors.groupingBy(MovimientoProdResponseDTO::getNombreUsuario, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Sin registros");
    }
}