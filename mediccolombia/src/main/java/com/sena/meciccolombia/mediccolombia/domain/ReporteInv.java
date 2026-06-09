package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "ReporteInv")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteInv implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name ="fecha_generacion")
    private LocalDateTime fechaGeneracion;

    @Column(name ="tipo_resultado")
    private String tipoResultado;

    @Column(name ="url_resultado")
    private String resultado;

    @Column(name ="tipo_reporte")
    private String tipoReporte;

    @ManyToOne
    @JoinColumn(name = "filtro_busqueda_id")
    private FiltroBusqueda filtroBusqueda;

    @ManyToOne
    @JoinColumn(name ="usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name ="movimiento_prod_id")
    private MovimientoProd movimientoProd;

    @ManyToOne
    @JoinColumn(name ="alerta_inv_id")
    private AlertaInv alertaInv; 
}
