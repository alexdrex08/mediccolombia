package com.sena.meciccolombia.mediccolombia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    private String resultadoProyeccion;

    private String referenciaTipo;

    private Integer pedidosEstimados;
    private LocalDateTime fechaGeneracion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private String unidad_medida;

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
