package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "Proyecciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proyecciones implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_proyecciones")
    private Long id;

    @Column(name ="resultado_proyeccion")
    private String resultadoProyeccion;

    @Column(name ="referencia_tipo")
    private String referenciaTipo;

    @Column(name ="pedidos_estimados")
    private Integer pedidosEstimados;

    @CreatedDate
    @Column(name ="fecha_generacion", nullable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    @Column(name ="fecha_inicio")
    private LocalDateTime fechaInicio;
    @Column(name ="fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name ="unidad_medida")
    private String unidadMedida;

    @ManyToOne
    @JoinColumn(name ="metodo_proyeccion_id")
    private MetodoProyeccion metodoProyeccion;

    @ManyToOne
    @JoinColumn(name ="tipo_proyeccion_id")
    private TipoProyeccion tipoProyeccion;

    @ManyToOne
    @JoinColumn(name ="producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name ="categoria_id")
    private Categoria categoria;


}
