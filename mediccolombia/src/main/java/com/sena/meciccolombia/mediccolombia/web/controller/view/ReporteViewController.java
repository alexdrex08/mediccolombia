package com.sena.meciccolombia.mediccolombia.web.controller.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sena.meciccolombia.mediccolombia.dao.DetalleFiltroDAO;
import com.sena.meciccolombia.mediccolombia.dao.FiltroBusquedaDAO;
import com.sena.meciccolombia.mediccolombia.dao.ReporteInvDAO;
import com.sena.meciccolombia.mediccolombia.domain.DetalleFiltro;
import com.sena.meciccolombia.mediccolombia.domain.FiltroBusqueda;
import com.sena.meciccolombia.mediccolombia.domain.ReporteInv;
import com.sena.meciccolombia.mediccolombia.security.MyUserDetails;
import com.sena.meciccolombia.mediccolombia.service.AlertaInvService;
import com.sena.meciccolombia.mediccolombia.service.CategoriaService;
import com.sena.meciccolombia.mediccolombia.service.IVentaRegistroService;
import com.sena.meciccolombia.mediccolombia.service.ProductoService;
import com.sena.meciccolombia.mediccolombia.service.ProveedorService;
import com.sena.meciccolombia.mediccolombia.service.ReporteInvService;
import com.sena.meciccolombia.mediccolombia.web.dto.request.ReporteInvRequestDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.ReporteDetalleResponseDTO;
import com.sena.meciccolombia.mediccolombia.web.dto.response.TotalVendidosDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteViewController {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    private static final DateTimeFormatter FMT_INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter FMT_PERIODO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ReporteInvService reporteInvService;
    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final AlertaInvService alertaInvService;
    private final ProveedorService proveedorService;
    private final IVentaRegistroService iVentaRegistroService;
    private final ReporteInvDAO reporteInvDAO;
    private final FiltroBusquedaDAO filtroBusquedaDAO;
    private final DetalleFiltroDAO detalleFiltroDAO;

    @GetMapping("/principal")
    public String reportesDashboard(Model model) {

        model.addAttribute("vistaActiva", "reportes-dashboard");

        model.addAttribute("totalReportes", reporteInvDAO.count());
        model.addAttribute("totalStock", reporteInvDAO.countByTipoReporte("REPORTE_STOCK"));
        model.addAttribute("totalVencimientos", reporteInvDAO.countByTipoReporte("REPORTE_VENCIMIENTOS"));
        model.addAttribute("totalProducto", reporteInvDAO.countByTipoReporte("REPORTE_PRODUCTO"));
        model.addAttribute("totalCategoria", reporteInvDAO.countByTipoReporte("REPORTE_CATEGORIA"));
        model.addAttribute("totalVentas", reporteInvDAO.countByTipoReporte("REPORTE_VENTAS"));
        model.addAttribute("totalPedidos", reporteInvDAO.countByTipoReporte("REPORTE_PEDIDOS"));
        model.addAttribute("totalClientes", reporteInvDAO.countByTipoReporte("REPORTE_CLIENTES"));
        model.addAttribute("totalProveedores", reporteInvDAO.countByTipoReporte("REPORTE_PROVEEDORES"));
        model.addAttribute("totalGeneral", reporteInvDAO.countByTipoReporte("REPORTE_GENERAL"));

        model.addAttribute("ultimosReportes", reporteInvDAO.findTop5ByOrderByFechaGeneracionDesc()
                .stream()
                .map(this::toResumenItem)
                .toList());

        return "reportes/reportes-dashboard";
    }

    @GetMapping("/inventario")
    public String reportesInventario(Model model) {
        model.addAttribute("vistaActiva", "reportes-stock");
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("historial", construirHistorial(
                "REPORTE_STOCK", "REPORTE_VENCIMIENTOS",
                "REPORTE_PRODUCTO", "REPORTE_CATEGORIA"));
        return "reportes/reportes-inventario";
    }

    @GetMapping("/general")
    public String reportesGeneral(Model model) {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia = LocalDate.now().atTime(23, 59, 59);

        int totalProductos = productoService.listarProductos().size();
        int totalAlertas = alertaInvService.listarIsResueltaFalse().size();
        int totalProveedores = proveedorService.listar().size();
        TotalVendidosDTO vendidoHoy = iVentaRegistroService.obtenerTotalVendidoEnElDia(inicioDia, finDia);
        BigDecimal totalVendido = vendidoHoy.getTotalVenta();

        model.addAttribute("vistaActiva", "reportes-general");
        model.addAttribute("historial", construirHistorial("REPORTE_GENERAL"));

        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("alertasActivas", totalAlertas);
        model.addAttribute("totalProveedores", totalProveedores);
        model.addAttribute("totalVendidoHoy", totalVendido);

        return "reportes/reportes-general";
    }

    @GetMapping("/ventas")
    public String reportesVentas(Model model) {
        model.addAttribute("vistaActiva", "reportes-ventas");
        model.addAttribute("historial", construirHistorial("REPORTE_VENTAS", "REPORTE_CLIENTES"));
        return "reportes/reportes-ventas";
    }

    @GetMapping("/pedidos")
    public String reportesPedidos(Model model) {
        model.addAttribute("vistaActiva", "reportes-proveedores");
        model.addAttribute("historial", construirHistorial("REPORTE_PEDIDOS", "REPORTE_PROVEEDORES"));
        return "reportes/reportes-pedidos";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GENERAR REPORTE
    // ─────────────────────────────────────────────────────────────────────────

    @PostMapping("/generar")
    public String generar(
            @RequestParam String tipoReporte,
            @RequestParam(required = false) Long idReferencia,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        LocalDateTime inicio = parsearFecha(fechaInicio);
        LocalDateTime fin = parsearFecha(fechaFin);

        Long idFiltroBusqueda = null;
        if (inicio != null || fin != null) {
            idFiltroBusqueda = crearFiltroPeriodo(inicio, fin);
        }

        ReporteInvRequestDTO dto = ReporteInvRequestDTO.builder()
                .tipoReporte(tipoReporte)
                .idUsuario(user.getId())
                .idReferencia(idReferencia)
                .idFiltroBusqueda(idFiltroBusqueda)
                .fechaInicio(inicio)
                .fechaFin(fin)
                .build();

        try {
            ReporteDetalleResponseDTO resultado = reporteInvService.generarReporte(dto);
            redirectAttributes.addFlashAttribute("mensaje",
                    "Reporte generado correctamente.");
            return "redirect:/reportes/" + resultado.getIdReporte();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al generar el reporte: " + e.getMessage());
            return "redirect:" + rutaGrupo(tipoReporte);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DETALLE DE UN REPORTE
    // ─────────────────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model,
            @RequestParam(required = false) String origen) {

        ReporteInv reporte = reporteInvDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        model.addAttribute("vistaActiva", "reportes-stock");
        model.addAttribute("reporteId", reporte.getId());
        model.addAttribute("tipoReporte", reporte.getTipoReporte());
        model.addAttribute("fechaGeneracion",
                reporte.getFechaGeneracion() != null
                        ? reporte.getFechaGeneracion().format(FMT)
                        : "—");
        model.addAttribute("nombreUsuario",
                reporte.getUsuario() != null ? reporte.getUsuario().getNombre() : "Sistema");
        model.addAttribute("urlResultado", reporte.getResultado());
        model.addAttribute("origen", origen != null ? origen : "inventario");

        model.addAttribute("periodoReporte",
                reporte.getFiltroBusqueda() != null
                        ? reporte.getFiltroBusqueda().getDescripcion()
                        : null);

        if (reporte.getContenidoJson() == null) {
            model.addAttribute("sinContenido", true);
            return "reportes/detalle-reporte";
        }

        try {
            Map<String, Object> c = MAPPER.readValue(
                    reporte.getContenidoJson(), new TypeReference<>() {
                    });

            switch (reporte.getTipoReporte()) {

                case "REPORTE_STOCK" -> {
                    model.addAttribute("totalProductos", c.get("totalProductos"));
                    model.addAttribute("critico", c.get("critico"));
                    model.addAttribute("normal", c.get("normal"));
                    model.addAttribute("exceso", c.get("exceso"));
                }
                case "REPORTE_VENCIMIENTOS" -> {
                    model.addAttribute("cantidadVencidos", c.get("cantidadVencidos"));
                    model.addAttribute("cantidadProximosAVencer", c.get("cantidadProximosAVencer"));
                    model.addAttribute("vencidos", c.get("vencidos"));
                    model.addAttribute("proximosAVencer", c.get("proximosAVencer"));
                }
                case "REPORTE_PRODUCTO" -> {
                    model.addAttribute("nombreProducto", c.get("producto"));
                    model.addAttribute("stock", c.get("stock"));
                    model.addAttribute("stockMinimo", c.get("stockMinimo"));
                    model.addAttribute("stockMaximo", c.get("stockMaximo"));
                    model.addAttribute("lote", c.get("lote"));
                    model.addAttribute("fechaExpiracion", c.get("fechaExpiracion"));
                    model.addAttribute("movimientos", c.get("movimientos"));
                    model.addAttribute("alertas", c.get("alertas"));
                }
                case "REPORTE_CATEGORIA" -> {
                    model.addAttribute("categoria", c.get("categoria"));
                    model.addAttribute("totalProductos", c.get("totalProductos"));
                    model.addAttribute("productos", c.get("productos"));
                }
                case "REPORTE_VENTAS" -> {
                    model.addAttribute("totalVentas", c.get("totalVentas"));
                    model.addAttribute("montoTotalVendido", c.get("montoTotalVendido"));
                    model.addAttribute("ventas", c.get("ventas"));
                }
                case "REPORTE_PEDIDOS" -> {
                    model.addAttribute("totalPedidos", c.get("totalPedidos"));
                    model.addAttribute("valorTotalPedidos", c.get("valorTotalPedidos"));
                    model.addAttribute("cantidadEstados", c.get("cantidadEstados"));
                    model.addAttribute("pedidosPorEstado", c.get("pedidosPorEstado"));
                }
                case "REPORTE_CLIENTES" -> {
                    model.addAttribute("totalClientes", c.get("totalClientes"));
                    model.addAttribute("clientesConCompras", c.get("clientesConCompras"));
                    model.addAttribute("clientesSinCompras", c.get("clientesSinCompras"));
                    model.addAttribute("totalFacturado", c.get("totalFacturado"));
                    model.addAttribute("clientes", c.get("clientes"));
                }
                case "REPORTE_PROVEEDORES" -> {
                    model.addAttribute("totalProveedores", c.get("totalProveedores"));
                    model.addAttribute("proveedoresConPedidos", c.get("proveedoresConPedidos"));
                    model.addAttribute("proveedoresSinPedidos", c.get("proveedoresSinPedidos"));
                    model.addAttribute("totalPedidos", c.get("totalPedidos"));
                    model.addAttribute("valorTotalCompras", c.get("valorTotalCompras"));
                    model.addAttribute("proveedores", c.get("proveedores"));
                }
                case "REPORTE_GENERAL" -> {
                    model.addAttribute("resumenGeneral", c.get("resumenGeneral"));
                    model.addAttribute("resumenInventario", c.get("inventario"));
                    model.addAttribute("resumenVentas", c.get("ventas"));
                    model.addAttribute("resumenCompras", c.get("compras"));
                    model.addAttribute("resumenClientes", c.get("clientes"));
                    model.addAttribute("resumenProveedores", c.get("proveedores"));
                    model.addAttribute("resumenAlertas", c.get("alertas"));
                    model.addAttribute("resumenVencimientos", c.get("vencimientos"));
                }
            }
        } catch (Exception e) {
            model.addAttribute("errorContenido",
                    "No se pudo leer el contenido del reporte: " + e.getMessage());
        }

        return "reportes/detalle-reporte";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HELPERS PRIVADOS
    // ─────────────────────────────────────────────────────────────────────────

    // METODO PARA PARSEAR LA FECHA
    private LocalDateTime parsearFecha(String valor) {
        if (valor == null || valor.isBlank())
            return null;
        try {
            return LocalDateTime.parse(valor, FMT_INPUT);
        } catch (Exception e) {
            return null;
        }
    }

    // METODO PARA CREAR EL FILTROBUSQUEDA
    private Long crearFiltroPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        String descipcion = "Período: "
                + (inicio != null ? inicio.format(FMT_PERIODO) : "incio")
                + " — "
                + (fin != null ? fin.format(FMT_PERIODO) : "fin");

        FiltroBusqueda filtro = FiltroBusqueda.builder()
                .descripcion(descipcion)
                .fechaCreacion(LocalDateTime.now())
                .build();
        FiltroBusqueda guardado = filtroBusquedaDAO.save(filtro);

        if (inicio != null) {
            detalleFiltroDAO.save(DetalleFiltro.builder()
                    .campoFiltro("fechaInicio")
                    .valorFiltro(inicio.toString())
                    .tipoDato("DATE")
                    .filtroBusqueda(guardado)
                    .build());
        }
        if (fin != null) {
            detalleFiltroDAO.save(DetalleFiltro.builder()
                    .campoFiltro("FechaFin")
                    .valorFiltro(fin.toString())
                    .tipoDato("DATE")
                    .filtroBusqueda(guardado)
                    .build());
        }
        return guardado.getId();

    }

    private List<Map<String, Object>> construirHistorial(String... tipos) {
        List<ReporteInv> todos = new ArrayList<>();
        for (String tipo : tipos) {
            todos.addAll(reporteInvDAO.findByTipoReporte(tipo));
        }
        return todos.stream()
                .sorted(Comparator.comparing(ReporteInv::getFechaGeneracion).reversed())
                .map(this::toResumenItem)
                .toList();
    }

    private Map<String, Object> toResumenItem(ReporteInv r) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("id", r.getId());
        item.put("tipoReporte", r.getTipoReporte());
        item.put("fechaGeneracion", r.getFechaGeneracion() != null
                ? r.getFechaGeneracion().format(FMT)
                : "—");
        item.put("nombreUsuario", r.getUsuario() != null ? r.getUsuario().getNombre() : "—");
        item.put("urlResultado", r.getResultado());
        item.put("asunto", extraerAsunto(r));
        item.put("periodo",
                r.getFiltroBusqueda() != null ? r.getFiltroBusqueda().getDescripcion() : null);
        return item;
    }

    private String extraerAsunto(ReporteInv r) {
        if (r.getContenidoJson() == null)
            return null;
        try {
            Map<String, Object> c = MAPPER.readValue(r.getContenidoJson(), new TypeReference<>() {
            });
            return switch (r.getTipoReporte()) {
                case "REPORTE_PRODUCTO" -> (String) c.get("producto");
                case "REPORTE_CATEGORIA" -> (String) c.get("categoria");
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }

    private String rutaGrupo(String tipo) {
        return switch (tipo) {
            case "REPORTE_VENTAS", "REPORTE_CLIENTES" -> "/reportes/ventas";
            case "REPORTE_PEDIDOS", "REPORTE_PROVEEDORES" -> "/reportes/pedidos";
            default -> "/reportes/inventario";
        };
    }
}